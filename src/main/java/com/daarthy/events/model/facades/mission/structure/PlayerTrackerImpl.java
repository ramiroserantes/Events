package com.daarthy.events.model.facades.mission.structure;

import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerTrackerImpl implements PlayerTracker {
    private final Map<ExtendedObjective, ObjectiveProgress> objectiveTrack = new HashMap<>();

    @Override
    public Map<ExtendedObjective, ObjectiveProgress> getObjectiveTrack() {
        return objectiveTrack;
    }

    @Override
    public Map<ExtendedObjective, ObjectiveProgress> trackAction(String target, Integer levels) {
        List<ExtendedObjective> matched = objectiveTrack.keySet().stream()
                .filter(key -> key.getObjective().matchTrack(target, levels))
                .toList();

        Map<ExtendedObjective, ObjectiveProgress> completedObjectives = new HashMap<>();

        matched.forEach(extendedObjective -> {
            Objective objective = extendedObjective.getObjective();
            ObjectiveProgress progress = objectiveTrack.get(extendedObjective);

            progress.increaseAmount(objective.getRequiredAmount());
            if (progress.getAmount() >= objective.getRequiredAmount()) {
                completedObjectives.put(extendedObjective, progress);
                objectiveTrack.remove(extendedObjective);
            }
        });

        return completedObjectives;
    }

    @Override
    public boolean isMissionCompleted(Long missionId) {
        return objectiveTrack.keySet().stream()
                .noneMatch(key -> key.getObjective().getMissionId().equals(missionId));
    }
}
