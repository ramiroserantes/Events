package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.UUID;

public interface DataCreationComp {

    /**
     * Creates a new player identified by the given UUID.
     *
     * <p>If the player does not exist in memory or persistent storage,
     * a new {@link EventsPlayer} is created and saved both in the persistent
     * storage and in memory. Additionally, it ensures that the player's
     * associated guild is present in memory, fetching it from persistent
     * storage if necessary.</p>
     *
     * @param playerId the unique identifier of the player to be created
     */
    EventsPlayer createPlayer(UUID playerId);

    /**
     * Creates a new guild with the specified parameters and associates it with
     * a creator player.
     *
     * <p>The method updates the player's data to associate them with the new
     * guild and saves both the player and the guild in persistent storage.
     * The created guild is also cached in memory for future use.</p>
     *
     * @param creator the unique identifier of the player who is creating the guild
     * @param guildId the unique identifier of the guild to be created
     * @param kName   the name of the guild
     */
    ExtendedGuild createGuild(UUID creator, Long guildId, String kName);
}
