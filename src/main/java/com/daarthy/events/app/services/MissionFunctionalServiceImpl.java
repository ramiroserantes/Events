package com.daarthy.events.app.services;

import com.daarthy.events.Events;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.Penalty;
import com.daarthy.events.app.modules.missions.Objective;
import com.daarthy.events.app.modules.missions.PlayerMissions;
import com.daarthy.events.persistence.factories.missions.MissionFactory;
import com.daarthy.events.persistence.factories.missions.MissionFactoryImpl;
import com.daarthy.events.persistence.daos.mission.*;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.zaxxer.hikari.HikariDataSource;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MissionFunctionalServiceImpl implements MissionFunctionalService {
    
    private Map<Long, Objective> objectives = new HashMap<>();
    private Map<UUID, PlayerMissions> playersObjs = new HashMap<>();

    private SecureRandom random = new SecureRandom();

    private MissionDao missionDao;
    private HikariDataSource dataSource;

    public MissionFunctionalServiceImpl(HikariDataSource dataSource, MissionDao missionDao) {
        this.dataSource = dataSource;
        this.missionDao = missionDao;
    }

    @Override
    public StringBuilder joinMission(UUID playerId, EventsPlayer eventsPlayer, Guild guild, Long missionId) {

        StringBuilder result = new StringBuilder();

        try (Connection connection = dataSource.getConnection()) {

            MissionData missionData = missionDao.findMissionById(missionId, connection);

            if(missionData.getExpiration().isBefore(LocalDate.now())) {
                return result.append(">> This mission is expired.");
            }

            if (missionDao.wasMissionAcceptedByPlayer(missionData.getMissionId(), playerId, connection)) {
                return result.append(">> You have already accepted this mission.");
            }

            if (missionDao.findAcceptedMissions(playerId, connection) >= eventsPlayer.getMaxMissions()) {
                return result.append(">> Max missions reached today.");
            }

            if (!missionData.addPlayer(guild.getGuildModifiers().getAmpMissions())) {
                return result.append(">> Max Mission capacity reached");
            }


            missionDao.saveMissionStatus(playerId, missionData.getMissionId(), MissionStatus.ACCEPTED, connection);

            List<ObjectiveData> missionObjs = missionDao.findMissionObjectivesByGuild(missionId, connection);

            for(ObjectiveData obj : missionObjs) {

                Objective objective = objectives.getOrDefault(obj.getObjectiveId(), null);

                if(objective == null) {
                    objective = new Objective(missionId, missionData.getExpiration(), missionData.getGrade(), obj.getTarget(), obj.getLevels()
                            , obj.getReqAmount());
                    objectives.put(obj.getObjectiveId(), objective);
                } else {
                    objective.updateObserved(+1);
                }
                missionDao.saveObjectiveProgress(playerId, obj.getObjectiveId(), 0, connection);
                playersObjs.get(playerId).putObjective(obj.getObjectiveId(), objective, 0);
            }

            return result.append("Mission Accepted");

        } catch (SQLException e) {
            //    Events.logInfo("Error in mission acceptance DB");
            return null;
        }
    }

    private Map<Grade, Integer> getNumberMissionsPerGrade(List<MissionData> missionDataList) {

        Map<Grade, Long> missionCountByGrade = missionDataList.stream()
                .collect(Collectors.groupingBy(MissionData::getGrade, Collectors.counting()));

        Map<Grade, Integer> result = new EnumMap<>(Grade.class);
        missionCountByGrade.forEach((grade, count) -> result.put(grade, count.intValue()));

        return result;
    }

    private int getMaxCountForGrade(Grade grade) {
        switch (grade) {
            case S: return 1;
            case A: return 2;
            case B: return 3;
            default: return Integer.MAX_VALUE;
        }
    }

    private void createMission(Connection connection, HashMap<MissionData, List<ObjectiveData>> mission, Long guildId) {

        List<ObjectiveData> objectiveDataList;

        for (Map.Entry<MissionData, List<ObjectiveData>> entry : mission.entrySet()) {

            MissionData missionData = entry.getKey();
            objectiveDataList = entry.getValue();
            missionData.setGuildId(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            objectiveDataList.forEach(item -> missionDao.createMissionObjective(missionId, item, connection));
        }

    }

    private void guildFactory(Map<Grade, Integer> grades, int slots, Connection connection,
                              boolean isDefaultGuild, Long guildId, Guild guild) {

        MissionFactory missionFactory = new MissionFactoryImpl();

        for (Grade grade : Grade.values()) {

            int gradeCount = grades.getOrDefault(grade, 0);
            int maxCount = getMaxCountForGrade(grade);

            while (gradeCount < slots && gradeCount < maxCount && slots > 0) {
                if (random.nextDouble() < (grade.getProbability() + guild.getAmplifiedProbability()) / 100.0) {
                    createMission(connection, missionFactory.getMission(grade, isDefaultGuild), guildId);
                    slots--;
                    gradeCount--;
                }
                gradeCount++;
            }
        }
    }
    @Override
    public void fillGuildDashBoard(Long guildId, Guild guild) {

        if (guild.getLastTimeUpdated().isBefore(LocalDate.now().atStartOfDay())) {
            try (Connection connection = dataSource.getConnection()) {

                List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);
                Map<Grade, Integer> grades = getNumberMissionsPerGrade(missionDataList);
                int slots = guild.getMaxMissions() - missionDataList.size();
                boolean isDefaultGuild = Objects.equals(guildId, Events.getBasicGuildId());

                if (slots > 0) {

                    guildFactory(grades, slots, connection, isDefaultGuild, guildId, guild);

                }
            } catch (SQLException e) {
                //  Events.logInfo("Error on creation of mission.");
            }
        }
    }

    @Override
    public List<Grade> addProgress(UUID playerId, String target, Integer level) {

        List<Grade> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            Map<Long, Grade> grades = playersObjs.get(playerId).addProgress(target, level);

            grades.forEach((key, value) -> {
                missionDao.saveMissionStatus(playerId, key, MissionStatus.FINALIZED, connection);
                result.add(value);
            });

        } catch (SQLException e) {
            //   Events.logInfo("Error on add Progress");
        }

        return result;
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> findPlayerDashBoard(UUID playerId) {

        Map<MissionData, List<ObjectiveData>> objec = new HashMap<>();
        Map<Long, Integer> playerProgress = playersObjs.get(playerId).getProgress();

        try (Connection connection = dataSource.getConnection()) {

            List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);

            for(MissionData item : missionDataList) {
                List<ObjectiveData> objectiveDataList = missionDao.findMissionObjectivesByPlayer(playerId,
                            item.getMissionId(), connection);

                for (ObjectiveData ob : objectiveDataList) {
                    Integer progressAmount = playerProgress.get(ob.getObjectiveId());
                    if (progressAmount != null) {
                        ob.setAmount(progressAmount);
                    }
                }
                objec.put(item, objectiveDataList);
            }

        } catch (SQLException e) {
            //   Events.logInfo("Error on Player Dashboard.");
        }

        return objec;
    }



    @Override
    public void savePlayer(UUID playerId) {

        Map<Long, Integer> data = playersObjs.get(playerId).getProgress();

        try (Connection connection = dataSource.getConnection()) {
            data.forEach((key, value) -> missionDao.saveObjectiveProgress(
                    playerId, key, value, connection));
        } catch (SQLException e) {
            //  Events.logInfo("Error on SavePlayer");
        }
    }

    @Override
    public Penalty initPlayer(UUID playerId) {

        Penalty failed = new Penalty();
        PlayerMissions playerMissions = new PlayerMissions();

        try (Connection connection = dataSource.getConnection()) {

            List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);
            List<MissionData> expiredMissions = new ArrayList<>();

            for (MissionData m : missionDataList) {
                if (m.getExpiration().isBefore(LocalDate.now())) {
                    missionDao.saveMissionStatus(playerId, m.getMissionId(), MissionStatus.FAILED, connection);
                    failed.addPenalty(m.getGuildId(), m.getGrade());
                    expiredMissions.add(m);
                }
            }

            missionDataList.removeAll(expiredMissions);

            List<ObjectiveData> objectiveDataList;

            for (MissionData ms : missionDataList) {

                objectiveDataList = missionDao.findMissionCurrentObjectivesByPlayer(playerId, ms.getMissionId(), connection);

                for (ObjectiveData objectiveData : objectiveDataList) {

                    Objective ob = objectives.getOrDefault(objectiveData.getObjectiveId(), null);

                    if (ob != null) {
                        ob.updateObserved(+1);
                    } else {
                        ob = new Objective(ms.getMissionId(), ms.getExpiration(),
                                ms.getGrade(), objectiveData.getTarget(), objectiveData.getLevels(),
                                objectiveData.getReqAmount());
                    }

                    objectives.put(objectiveData.getObjectiveId(), ob);
                    playerMissions.putObjective(objectiveData.getObjectiveId(), ob, objectiveData.getAmount());

                }
            }

            playersObjs.put(playerId, playerMissions);

        } catch (SQLException e) {
            //    Events.logInfo("Error on player init");
        }
        return failed;
    }


    @Override
    public void removePlayer(UUID playerId) {
        savePlayer(playerId);
        playersObjs.get(playerId).getObjectives().forEach((key, value) -> value.updateObserved(-1));
        playersObjs.remove(playerId);
    }

    @Override
    public void removeNonWatchedObjectives() {

        Iterator<Map.Entry<Long, Objective>> iterator = objectives.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Objective> entry = iterator.next();
            Objective objective = entry.getValue();
            if (!objective.isObserved()) {
                iterator.remove();
            }
        }
    }

    @Override
    public Penalty removeFailedMissions(UUID playerId) {

        Penalty penalty = new Penalty();

        List<Long> toRemoveObjectives = new ArrayList<>();
        List<Long> toRemoveMissions = new ArrayList<>();

        PlayerMissions playerMissions = playersObjs.get(playerId);

        playerMissions.getObjectives().forEach((key, value) -> {

            if(value.getExpirationDate().isBefore(LocalDate.now()) ||
                    value.getExpirationDate().isEqual(LocalDate.now())) {
                if(!toRemoveMissions.contains(value.getMissionId())) {
                    toRemoveMissions.add(value.getMissionId());
                }
                toRemoveObjectives.add(key);
            }
        });

        toRemoveObjectives.forEach(playerMissions::removeObjective);

        try (Connection connection = dataSource.getConnection()) {

            for(Long missionId : toRemoveMissions) {
                MissionData missionData = missionDao.findMissionById(missionId, connection);
                missionDao.saveMissionStatus(playerId, missionData.getMissionId(), MissionStatus.FAILED, connection);
                penalty.addPenalty(missionData.getGuildId(), missionData.getGrade());
            }


        } catch (SQLException e) {
            //   Events.logInfo("Error on Midnight update");
        }


        return penalty;
    }

    public Map<Long, Objective> getObjectives() {
        return objectives;
    }

    public Map<UUID, PlayerMissions> getPlayersObjs() {
        return playersObjs;
    }
}
