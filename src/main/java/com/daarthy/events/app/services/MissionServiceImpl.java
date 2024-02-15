package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.GuildCache;
import com.daarthy.events.app.modules.missions.Objective;
import com.daarthy.events.app.modules.missions.ObservableObjective;
import com.daarthy.events.app.modules.missions.PlayerSubject;
import com.daarthy.events.app.modules.missions.PlayerSubjectImpl;
import com.daarthy.events.persistence.missionDao.MissionDao;
import com.daarthy.events.persistence.missionDao.MissionData;
import com.daarthy.events.persistence.missionDao.MissionStatus;
import com.daarthy.events.persistence.missionDao.ObjectiveData;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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

            List<ObservableObjective> objectives = missions.computeIfAbsent(missionData.getMissionId(),
                    key -> missionDao.findMissionObjectivesByPlayer(playerId, key, connection)
                            .stream()
                            .map(ob -> new Objective(ob.getObjectiveId(), ob.getTarget(), ob.getLevels(), ob.getReqAmount()))
                            .collect(Collectors.toList()));

            objectives.forEach(objective -> objective.updateObserved(1));

            PlayerSubject playerSubject = playerMissions.get(playerId);

            // It will never be null since initPlayer(); must create the object.
            playerSubject.addMission(missionData.getMissionId(), objectives);

            return result.append("Mission Accepted");
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

    @Override
    public void fillGuildDashBoard(Long guildId, GuildCache guildCache) {

        if(guildCache.getLastTimeUpdated().isBefore(LocalDate.now().atStartOfDay())) {

            try (Connection connection = dataSource.getConnection()) {

                List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);

                int difference = missionDataList.size() - guildCache.getMaxMissions();

                if(difference > 0) {
                    missionDataList.forEach(item ->
                            item.getGrade());
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }


    @Override
    public void savePlayer(UUID playerId) {

        PlayerSubject playerSubject = playerMissions.get(playerId);

        try (Connection connection = dataSource.getConnection()) {
            playerSubject.getProgress().forEach(
                    (key, value) -> missionDao.saveObjectiveProgress(playerId, key.getObjectiveId(), value, connection));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


}
