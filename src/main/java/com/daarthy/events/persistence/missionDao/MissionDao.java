package com.daarthy.events.persistence.missionDao;

import com.daarthy.events.app.modules.missions.Grade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MissionDao {

    Long createMission(MissionData missionData, Connection connection);

    //TEST LATER IF NEEDED
    MissionData findMissionById(Long missionId, Connection connection);

    //TEST LATER IF NEEDED
    boolean wasMissionAcceptedByPlayer(Long missionId, UUID playerId, Connection connection);

    int findAcceptedMissions(UUID playerId, Connection connection);

    List<MissionData> findGuildMissions(Long guildId, Connection connection);

    //Create Player ACCEPT
    List<MissionData> findPlayerMissions(UUID playerId, Connection connection);

    //CREATE PLAYER
    Long createMissionObjective(Long missionId, ObjectiveData objectiveData, Connection connection);

    //ByGuild AND BYPlayer
    List<ObjectiveData> findMissionObjectivesByGuild(Long missionId, Connection connection);

    List<ObjectiveData> findMissionObjectivesByPlayer(UUID playerId, Long missionId, Connection connection);

    HashMap<String, CompletionData> findPlayerCompletionsRate(UUID playerId, Connection connection);

    void saveMissionStatus(UUID playerId, Long missionId, MissionStatus status, Connection connection);

    void saveObjectiveProgress(UUID playerId, Long objectiveId, int amount, Connection connection);

    List<FailedMission> findFailedMissionsByGuild(Long guildId, Connection connection);
}
