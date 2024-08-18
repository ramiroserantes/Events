package com.daarthy.events.persistence.daos.mission;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MissionDao {

    Long createMission(MissionData missionData, Connection connection);

    MissionData findMissionById(Long missionId, Connection connection);

    boolean wasMissionAcceptedByPlayer(Long missionId, UUID playerId, Connection connection);

    int findAcceptedMissions(UUID playerId, Connection connection);

    List<MissionData> findGuildMissions(Long guildId, Connection connection);

    List<MissionData> findPlayerMissions(UUID playerId, Connection connection);

    Long createMissionObjective(Long missionId, ObjectiveData objectiveData, Connection connection);

    List<ObjectiveData> findMissionObjectivesByGuild(Long missionId, Connection connection);

    List<ObjectiveData> findMissionObjectivesByPlayer(UUID playerId, Long missionId, Connection connection);

    List<ObjectiveData> findMissionCurrentObjectivesByPlayer(UUID playerId, Long missionId, Connection connection);

    HashMap<String, CompletionData> findPlayerCompletionsRate(UUID playerId, Connection connection);

    void saveMissionStatus(UUID playerId, Long missionId, MissionStatus status, Connection connection);

    void saveObjectiveProgress(UUID playerId, Long objectiveId, int amount, Connection connection);

}
