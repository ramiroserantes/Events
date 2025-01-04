package com.daarthy.events.model.facades.data.components;

import java.util.UUID;

public interface DataRemovalComp {

    /**
     * Logs out a player from the application by persisting their current state
     * to the database and removing them from the in-memory cache.
     *
     * @param playerId the unique identifier of the player to log out.
     */
    void logOutPlayer(UUID playerId);

    /**
     * Deletes a guild and its associated players from the application.
     * This operation ensures that all players in the guild are saved
     * to the database, removes them from the cache, and deletes the guild
     * from persistent storage.
     *
     * @param guildId the unique identifier of the guild to delete.
     */
    void deleteGuild(Long guildId);

}
