package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.UUID;

/**
 * The DataFinderComp interface defines methods for retrieving
 * and caching data related to players and guilds in the application.
 *
 * <p>This is a read-only component, intended to be injected into
 * other components that require access to player or guild data.</p>
 */
public interface DataFinderComp {

    /**
     * Retrieves an EventsPlayer by their unique player ID.
     * If the player is not found in memory, the method attempts to
     * fetch the player from persistent storage and caches the result.
     *
     * @param playerId the unique identifier of the player
     * @return the {@link EventsPlayer} associated with the given ID,
     * either from memory or persistent storage
     */
    EventsPlayer findEventsPlayer(UUID playerId);

    /**
     * Retrieves an ExtendedGuild by its unique guild ID.
     * If the guild is not found in memory, the method attempts to
     * fetch the guild from persistent storage, wraps it in a GuildAdapter,
     * and caches the result.
     *
     * @param guildId the unique identifier of the guild
     * @return the {@link ExtendedGuild} associated with the given ID,
     * either from memory or persistent storage
     */
    ExtendedGuild findExtendedGuild(Long guildId);

    /**
     * Retrieves an ExtendedGuild by its unique guild ID.
     * If the guild is found in memory, it is returned directly.
     * If the guild is not found in memory, it attempts to fetch the guild
     * from persistent storage, wraps it in a GuildAdapter, but does not update the cache.
     *
     * @param guildId the unique identifier of the guild
     * @return the {@link ExtendedGuild} associated with the given ID,
     * either from memory or fetched from persistent storage, but not cached.
     */
    ExtendedGuild findPersistedGuild(Long guildId);
}
