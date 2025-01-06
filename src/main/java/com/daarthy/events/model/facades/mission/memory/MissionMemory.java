package com.daarthy.events.model.facades.mission.memory;

import com.daarthy.events.model.facades.mission.structure.ExtendedObjective;
import com.daarthy.events.model.facades.mission.structure.PlayerTracker;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public interface MissionMemory {

    /**
     * Saves the progress of all players objectives to persistent storage.
     * This method should collect all the ObjectiveProgress instances
     * from tracked players and save them in bulk.
     */
    void savePlayersProgress();

    /**
     * Adds an objective to the tracking system for a specific player.
     * If the player is not already being tracked, a new player tracker will be created.
     *
     * @param mission   The mission associated with the objective.
     * @param objective The objective to be added.
     * @param playerId  The unique identifier of the player.
     */
    void addObjective(Mission mission, Objective objective, UUID playerId);

    /**
     * Updates the count of players observing that objective, and remove it later.
     *
     * @param objective The objective to be removed from the tracking system.
     */
    void removeObjectiveFromCache(ExtendedObjective objective);

    /**
     * Retrieves the map of observed objectives being tracked.
     *
     * @return A map where the key is an {@link ExtendedObjective} and the value
     * is an {@link AtomicReference} representing the current players watching that objective.
     */
    Map<ExtendedObjective, AtomicReference<Long>> getObservedObjectives();

    /**
     * Retrieves the map of player objectives being tracked.
     *
     * @return A map where the key is a unique player identifier (UUID) and the value
     * is a {@link PlayerTracker} containing the player's tracked objectives.
     */
    Map<UUID, PlayerTracker> getPlayerObjectives();
}
