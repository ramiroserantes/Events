package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;

import java.util.List;
import java.util.UUID;

public interface MissionRegistryComp {

    /**
     * Allows a player to join a mission while ensuring thread safety through synchronization.
     *
     * <p>This method performs the following operations:
     * <ul>
     *   <li>Checks the player's eligibility to join the mission using a set of validation rules.</li>
     *   <li>Registers the mission acceptance if validation passes.</li>
     *   <li>Initializes mission objectives and tracks the player's progress in both memory and database.</li>
     *   <li>Ensures thread safety during the operation to prevent inconsistent states.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Note:</strong> The implementation of this method is synchronized, ensuring that only one
     * thread can execute it at a time.</p>
     *
     * @param miniAuth      Authentication context of the current player.
     * @param eventsPlayer  The player attempting to join the mission.
     * @param extendedGuild The guild associated with the mission.
     * @param missionId     The unique identifier of the mission to join.
     * @return A {@link StringBuilder} containing messages about the outcome of the operation.
     */
    StringBuilder joinMission(MiniAuth miniAuth, EventsPlayer eventsPlayer, ExtendedGuild extendedGuild,
                              Long missionId);

    /**
     * Adds progress to a specific player's objectives, updates their progress, and finalizes missions if necessary.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Tracks the player's progress for the given target and levels.</li>
     *   <li>Removes completed objectives from the cache and saves the progress in the database.</li>
     *   <li>Finalizes the mission if all objectives have been completed and updates the mission status.</li>
     *   <li>Returns a list of grades for completed missions.</li>
     * </ul>
     *
     * @param playerId The unique identifier of the player.
     * @param target   The target representing the objective to track.
     * @param levels   The number of levels or progress units to add.
     * @return A list of grades for the completed missions.
     */
    List<Grade> addProgress(UUID playerId, String target, Integer levels);

}
