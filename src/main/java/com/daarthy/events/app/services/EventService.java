package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.mission_dao.ActionType;

import java.util.*;

public interface EventService {

    EventToken registerAction(UUID playerId, Guild guild, ActionType actionType);

    List<StringBuilder> getActiveEvents();

    StringBuilder getEventInfo(String eventName);

    List<Long> removeInactiveEvents();

    void setUpEvents();

    void initPlayer(UUID playerId);

    void removePlayer(UUID playerId);

}
