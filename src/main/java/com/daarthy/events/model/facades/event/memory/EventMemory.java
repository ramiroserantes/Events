package com.daarthy.events.model.facades.event.memory;

import com.daarthy.events.model.facades.event.structure.ExtendedEvent;
import com.daarthy.events.persistence.daos.event.entities.GuildMedals;
import com.daarthy.events.persistence.daos.event.entities.GuildMedalsKey;

import java.util.Map;
import java.util.Set;

public interface EventMemory {

    /**
     * Removes events that are no longer active based on their end date.
     * Inactive events are those whose end date is before the current date.
     */
    void removeInactiveEvents();

    /**
     * Saves the progress of all players involved in the active events.
     * Player progress includes contributions and associated data.
     */
    void savePlayersProgress();

    /**
     * Saves the medals earned by guilds during the events.
     * Guild medals are persisted into the associated database or memory structure.
     */
    void saveGuildMedals();

    /**
     * Fetches and uploads the current events into memory.
     * Currently, only events with the "ALL" world scope are allowed.
     * The events are retrieved from the database and mapped into ExtendedEvent objects.
     */
    void uploadEvents();

    /**
     * Normal methods
     */
    Set<ExtendedEvent> getEvents();

    Map<GuildMedalsKey, GuildMedals> getGuildMedals();
}
