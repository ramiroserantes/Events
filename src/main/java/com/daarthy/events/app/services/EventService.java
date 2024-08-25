package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.events.Event;
import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.EventMedals;
import com.daarthy.events.persistence.daos.event.EventData;

import java.util.*;

public interface EventService {

    // Default guild should not enter this service.
   /* List<EventToken> registerAction(UUID playerId, Long guildId, ActionType actionType);

    List<Event> getActiveEvents();

    //this requires a configFile.
    StringBuilder getEventInfo(UUID playerId, String eventName);

    List<EventData> removeInactiveEvents();

    EventMedals getGuildMedals(Long guildId);

    void setUpEvents();

    void initPlayer(UUID playerId, Long guildId);

    void removeGuild(Long guildId);

    void saveGuild(Long guildId);

    void saveAllGuilds();

    void savePlayer(UUID playerId);

    void removePlayer(UUID playerId);*/

}
