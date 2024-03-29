package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.GuildCache;
import com.daarthy.events.app.modules.missions.factory.MissionFactory;
import com.daarthy.events.app.modules.missions.factory.MissionFactoryImpl;
import com.daarthy.events.app.modules.missions.observers.Objective;
import com.daarthy.events.app.modules.missions.observers.ObservableObjective;
import com.daarthy.events.app.modules.missions.observers.PlayerSubject;
import com.daarthy.events.app.modules.missions.observers.PlayerSubjectImpl;
import com.daarthy.events.persistence.missionDao.*;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService{

    private HashMap<Long, List<ObservableObjective>> missions = new HashMap<>();
    private HashMap<UUID, PlayerSubject> playerMissions = new HashMap<>();

    private final HikariDataSource dataSource;
    private final MissionDao missionDao;

    public MissionServiceImpl(MissionDao missionDao, HikariDataSource hikariDataSource) {
        this.missionDao = missionDao;
        this.dataSource = hikariDataSource;
    }

    @Override
    public StringBuilder joinMission(UUID playerId, PlayerData playerData, GuildCache guildCache, MissionData missionData) {
        StringBuilder result = new StringBuilder();

        try (Connection connection = dataSource.getConnection()) {
            if (missionDao.wasMissionAcceptedByPlayer(missionData.getMissionId(), playerId, connection)) {
                return result.append("You have already completed this mission.");
            }

            if (missionDao.findAcceptedMissions(playerId, connection) >= playerData.getMaxMissions()) {
                return result.append("Max missions reached today");
            }

            if (!missionData.addPlayer(guildCache.getAmpMissions())) {
                return result.append("Max Mission capacity reached");
            }

            missionDao.saveMissionStatus(playerId, missionData.getMissionId(), MissionStatus.ACCEPTED, connection);

            //If missions doesn't have this mission it should be set.
            List<ObservableObjective> objectives = missions.computeIfAbsent(missionData.getMissionId(),
                    key -> missionDao.findMissionObjectivesByGuild(missionData.getMissionId(), connection)
                            .stream()
                            .map(ob -> new Objective(ob.getObjectiveId(), ob.getTarget(), ob.getLevels(), ob.getReqAmount()))
                            .collect(Collectors.toList()));

            objectives.forEach(objective -> {
                missionDao.saveObjectiveProgress(playerId, objective.getObjectiveId(), 0, connection);
                objective.updateObserved(1);
            });

            PlayerSubject playerSubject = playerMissions.get(playerId);

            // It will never be null since initPlayer(); must create the object.
            playerSubject.addMission(missionData.getMissionId(), objectives);

            return result.append("Mission Accepted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fillGuildDashBoard(Long guildId, GuildCache guildCache) {

        if (guildCache.getLastTimeUpdated().isBefore(LocalDate.now().atStartOfDay())) {
            try (Connection connection = dataSource.getConnection()) {

                List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);
                HashMap<Grade, Integer> grades = getNumberMissionsPerGrade(missionDataList);
                int slots = guildCache.getMaxMissions() - missionDataList.size();
                boolean isDefaultGuild = guildId == 1L;

                if (slots > 0) {
                    MissionFactory missionFactory = new MissionFactoryImpl();

                    for (Grade grade : Grade.values()) {

                        int gradeCount = grades.getOrDefault(grade, 0);
                        int maxCount = getMaxCountForGrade(grade);

                        while (gradeCount < slots && gradeCount < maxCount && slots > 0) {
                            if (Math.random() < (grade.getProbability() + guildCache.getAmplifiedProbability()) / 100.0) {
                                createMission(connection, missionFactory.getMission(grade, isDefaultGuild), guildId);
                                slots--;
                                gradeCount--;
                            }
                            gradeCount++;
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<FailedMission> updateFailedMissions(Long guildId) {

        List<FailedMission> result;

        try (Connection connection = dataSource.getConnection()) {

            result = missionDao.findFailedMissionsByGuild(guildId, connection);

            result.forEach(item -> {
                missions.remove(item.getMissionId());
                playerMissions.forEach((key, value) ->
                        value.deleteMission(item.getMissionId()));
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Grade> addProgress(UUID playerId, String target, Integer level) {

        List<Grade> result = new ArrayList<>();
        PlayerSubject playerSubject = playerMissions.get(playerId);

        try (Connection connection = dataSource.getConnection()) {

            playerSubject.addProgress(target, level).forEach(item -> {

                boolean observed = false;
                //Objective saved.
                missionDao.saveObjectiveProgress(playerId, item.getObjectiveId(),
                        item.getReqAmount(), connection);

                // Mission completed ? -> is anyone watching it?
                // yes -> Dont remove , no -> Remove from cache.
                for(Map.Entry<Long, List<ObservableObjective>> entry : playerSubject.getMissionObjectives().entrySet()) {

                    if(playerSubject.isMissionCompleted(entry.getKey())) {

                        playerSubject.deleteMission(entry.getKey());

                        for(ObservableObjective obs : missions.get(entry.getKey())) {
                            if(obs.isObserved()) {
                                observed = true;
                            }
                            break;
                        }

                        if(!observed) {
                            missions.remove(entry.getKey());
                        }

                        result.add(Grade.valueOf(missionDao.findMissionById(
                                entry.getKey(), connection).getGrade()));
                    }
                }

            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public HashMap<MissionData, List<ObjectiveData>> findGuildDashBoard(Long guildId) {

        HashMap<MissionData, List<ObjectiveData>> objectives = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {

            List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);

            missionDataList.forEach(item ->
                    objectives.put(item, missionDao.findMissionObjectivesByGuild(item.getMissionId(), connection))
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objectives;

    }

    @Override
    public HashMap<MissionData, List<ObjectiveData>> findPlayerDashBoard(UUID playerId) {

        HashMap<MissionData, List<ObjectiveData>> objectives = new HashMap<>();
        PlayerSubject playerSubject = playerMissions.get(playerId);

        try (Connection connection = dataSource.getConnection()) {

            List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);

            missionDataList.forEach(item -> {

                List<ObjectiveData> objectiveDataList = missionDao.findMissionObjectivesByPlayer(playerId,
                        item.getMissionId(), connection);

                for (ObjectiveData ob : objectiveDataList) {
                    Integer progressAmount = playerSubject.getProgressByObjective(ob.getObjectiveId());
                    if (progressAmount != null) {
                        ob.setAmount(progressAmount);
                    }
                }

                objectives.put(item, objectiveDataList);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objectives;
    }

    @Override
    public HashMap<String, CompletionData> getPlayerRates(UUID playerId) {

        try (Connection connection = dataSource.getConnection()) {

            return missionDao.findPlayerCompletionsRate(playerId, connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void savePlayer(UUID playerId) {

        PlayerSubject playerSubject = playerMissions.get(playerId);

        try (Connection connection = dataSource.getConnection()) {
            playerSubject.getProgress().forEach(
                    (key, value) -> missionDao.saveObjectiveProgress(
                            playerId, key.getObjectiveId(), value, connection));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initPlayer(UUID playerId) {
        if (!playerMissions.containsKey(playerId)) {
            try (Connection connection = dataSource.getConnection()) {

                List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);
                HashMap<Long, List<ObservableObjective>> playerMap = new HashMap<>();
                PlayerSubject playerSubject;
                List<ObjectiveData> objectives = new ArrayList<>();

                for (MissionData ms : missionDataList) {

                    objectives = missionDao.findMissionObjectivesByPlayer(playerId, ms.getMissionId(), connection);

                    if (!missions.containsKey(ms.getMissionId())) {
                        missions.put(ms.getMissionId(), getCacheObjectives(objectives));
                    } else {
                        playerMap.put(ms.getMissionId(), missions.get(ms.getMissionId()));
                    }
                }

                playerSubject = new PlayerSubjectImpl(playerMap, getPlayerProgress(objectives));
                playerMissions.put(playerId, playerSubject);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private List<ObservableObjective> getCacheObjectives(List<ObjectiveData> objectiveDataList) {
        return objectiveDataList.stream()
                .map(ob -> new Objective(ob.getObjectiveId(), ob.getTarget(), ob.getLevels(), ob.getReqAmount()))
                .collect(Collectors.toList());
    }

    private HashMap<ObservableObjective, Integer> getPlayerProgress(List<ObjectiveData> objectiveDataList) {
        return new HashMap<>(objectiveDataList.stream()
                .collect(Collectors.toMap(
                        ob -> new Objective(ob.getObjectiveId(), ob.getTarget(), ob.getLevels(), ob.getReqAmount()),
                        ObjectiveData::getAmount
                )));
    }

    private void removeObjectiveFromMissions(Long missionId, ObservableObjective objective) {
        missions.computeIfPresent(missionId, (key, objectives) -> {
            objectives.remove(objective);
            return objectives.isEmpty() ? null : objectives;
        });
    }

    private HashMap<Grade, Integer> getNumberMissionsPerGrade(List<MissionData> missionDataList) {
        Map<String, Long> missionCountByGrade = missionDataList.stream()
                .collect(Collectors.groupingBy(MissionData::getGrade, Collectors.counting()));

        HashMap<Grade, Integer> result = new HashMap<>();
        missionCountByGrade.forEach((grade, count) -> result.put(Grade.valueOf(grade), count.intValue()));

        return result;
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

    @Override
    public void removePlayer(UUID playerId) {

        savePlayer(playerId);

        PlayerSubject playerSubject = playerMissions.get(playerId);

        playerSubject.getMissionObjectives().forEach((key, value) ->
                value.forEach(item -> {
                    item.updateObserved(-1);
                    if (!item.isObserved()) {
                        removeObjectiveFromMissions(key, item);
                    }
                })
        );

        playerMissions.remove(playerId);

    }

    private int getMaxCountForGrade(Grade grade) {
        switch (grade) {
            case S: return 1;
            case A: return 2;
            case B: return 3;
            default: return Integer.MAX_VALUE;
        }
    }

}
