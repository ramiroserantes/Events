package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.Penalty;
import com.daarthy.events.persistence.daos.mission.MissionData;
import com.daarthy.events.persistence.daos.mission.ObjectiveData;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MissionFunctionalService {

    /*StringBuilder joinMission(UUID playerId, EventsPlayer eventsPlayer, Guild guild, Long missionId);

    void fillGuildDashBoard(Long guildId, Guild guild);

    List<Grade> addProgress(UUID playerId, String target, Integer level);

    Map<MissionData, List<ObjectiveData>> findPlayerDashBoard(UUID playerId);

    // After all the midnightUpdate requires to use removeNonWatchedObjectives.
    Penalty removeFailedMissions(UUID playerId);

    /**
     *
     * Cache Ops.
     * **/

  /*  void savePlayer(UUID playerId);

    Penalty initPlayer(UUID playerId);

    void removePlayer(UUID playerId);

    void removeNonWatchedObjectives();
*/

}
