package com.daarthy.events.app.modules.missions;

import com.daarthy.events.persistence.daos.mission.Grade;

import java.util.*;

public class PlayerMissions {

    private Map<Long, Objective> objectives = new HashMap<>();
    private Map<Long, Integer> progress = new HashMap<>();


    private Map<Long, Grade> findObjectiveCompleted(String target, Integer level) {

        Map<Long, Grade> found = new HashMap<>();
        List<Long> toRemove = new ArrayList<>();

        objectives.forEach((key, value) -> {
            if(value.match(target, level)) {
                Integer currentProgress = progress.get(key);
                currentProgress += 1;
                progress.put(key, currentProgress);
                if (currentProgress >= value.getReqAmount()) {
                    found.put(value.getMissionId(), value.getGrade());
                    toRemove.add(key);
                }
            }
        });

        toRemove.forEach(this::removeObjective);

        return found;
    }

    public void removeObjective(Long objectiveId) {
        objectives.get(objectiveId).updateObserved(-1);
        objectives.remove(objectiveId);
        progress.remove(objectiveId);
    }

    public Map<Long, Grade> addProgress(String target, Integer level) {
        // MissionId + Grade
        Map<Long, Grade> foundObjectives = findObjectiveCompleted(target, level);
        Map<Long, Grade> completed = new HashMap<>();

        for (Map.Entry<Long, Grade> entry : foundObjectives.entrySet()) {
            Long missionId = entry.getKey();
            if (objectives.values().stream().noneMatch(obj -> obj.getMissionId().equals(missionId))) {
                completed.put(missionId, entry.getValue());
            }
        }

        return completed;
    }

    public void putObjective(Long entryKey, Objective entryValue, Integer objProgress) {
        objectives.put(entryKey, entryValue);
        progress.put(entryKey, objProgress);
    }

    public Map<Long, Integer> getProgress() {
        return progress;
    }

    public Map<Long, Objective> getObjectives() {
        return objectives;
    }

    public Integer getProgress(Long objId) {
        return progress.getOrDefault(objId, null);
    }
}
