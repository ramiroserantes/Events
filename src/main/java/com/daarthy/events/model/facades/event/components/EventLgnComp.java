package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

public interface EventLgnComp {

    /**
     * Logs a player into the system and associates them with any relevant ongoing events.
     *
     * <p>When a player logs in, their existing contributions to events are loaded from
     * the database, and they are added to the memory of active players for the respective events.
     * If the player's guild is associated with a guild-specific event, their guild's medals
     * data is also initialized or updated.</p>
     *
     * @param player the {@link EventsPlayer} object representing the player logging in.
     */
    void loginPlayer(EventsPlayer player);

    /**
     * Logs a player out of the system and removes their association with active events.
     *
     * <p>When a player logs out, their contributions to events are saved to the database,
     * and they are removed from the memory of active players for the respective events.</p>
     *
     * @param player the {@link EventsPlayer} object representing the player logging out.
     */
    void logoutPlayer(EventsPlayer player);
}
