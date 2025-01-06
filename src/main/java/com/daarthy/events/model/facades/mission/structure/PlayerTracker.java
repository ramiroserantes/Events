package com.daarthy.events.model.facades.mission.structure;

import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;

import java.util.Map;

public interface PlayerTracker {

    /**
     * Returns the current tracking of extended objectives and their progress.
     *
     * @return a map of objectives and their progress.
     */
    Map<ExtendedObjective, ObjectiveProgress> getObjectiveTrack();

    /**
     * Tracks an action performed by the player and updates objectives accordingly.
     *
     * @param target the action target to match objectives.
     * @param levels the levels associated with the action.
     * @return a map of completed objectives and their final progress.
     */
    Map<ExtendedObjective, ObjectiveProgress> trackAction(String target, Integer levels);

    /**
     * Checks if a mission is completed based on its ID.
     *
     * @param missionId the ID of the mission.
     * @return true if the mission is completed (not tracked), false otherwise.
     */
    boolean isMissionCompleted(Long missionId);
}
