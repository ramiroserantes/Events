package com.daarthy.events.api;

import com.daarthy.events.app.modules.events.Event;
import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.daos.mission.*;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.enums.festivals.ActionType;
import com.daarthy.mini.shared.enums.festivals.Grade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ApiGateway {

    void logInRequest(UUID playerId);

    void logOutRequest(UUID playerId);

    /**
     *
     * Data Related Request
     *
     * **/
    EventsPlayer getPlayerDataRequest(UUID playerId);

    Guild getGuildDataRequest(UUID playerId);

    int getMaxJobLevelRequest(UUID playerId);

    void deleteGuildRequest(Long guildId);

    void createGuildRequest(UUID playerId, Long guildId, String kName);

    void savePlayerRequest(UUID playerId);

    /**
     *
     * Event relatedRequest
     *
     * **/

    List<EventToken> eventActivityRequest(UUID playerId, ActionType actionType);

    List<Event> getActiveEventsRequest();

    StringBuilder getEventInfoRequest(UUID playerId, String eventName);

    int getGuildMedalsOnEventRequest(UUID playerId, Long eventId);


    /**
     *
     * Missions relatedRequests
     *
     */

    List<Grade> missionActivityRequest(UUID playerId, String target, Integer level);

    StringBuilder joinMissionRequest(UUID playerId, Long missionId);

    Map<MissionData, List<ObjectiveData>> getPlayerDashBoardRequest(UUID playerId);

    Map<MissionData, List<ObjectiveData>> getGuildDashBoardRequest(UUID playerId);

    Map<String, CompletionData> getPlayerRatesRequest(UUID playerId);

    AppContainer getContainer();

}
