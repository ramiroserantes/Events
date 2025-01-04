package com.daarthy.events.model.facades.data.memory;

import com.daarthy.events.model.facades.data.structure.GuildAdapter;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.Map;
import java.util.UUID;

public interface DataMemory {

    /**
     * Removes a player from the in-memory data structure.
     * If the player exists in the data cache, their data is persisted before removal.
     *
     * @param playerId the unique identifier of the player to be removed.
     */
    void removePlayer(UUID playerId);

    /**
     * Removes unused guilds from the in-memory data structure.
     * Guilds that are not referenced by any player are persisted to the database and then removed from the cache.
     */
    void removeUnusedGuilds();

    /**
     * Removes all players from a specific guild.
     * Players belonging to the specified guild will have their guild association reset to a default value.
     * After updating the players, unused guilds are also removed from the cache.
     *
     * @param guildId the unique identifier of the guild from which players are to be removed.
     */
    void removeAllPlayersFromGuild(Long guildId);

    /**
     * Normal methods
     */
    Map<UUID, EventsPlayer> getPlayersData();

    Map<Long, GuildAdapter> getGuildsData();
}
