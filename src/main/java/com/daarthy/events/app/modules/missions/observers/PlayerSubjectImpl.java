package com.daarthy.events.app.modules.missions.observers;

import java.util.*;

public class PlayerSubjectImpl implements PlayerSubject {

    HashMap<Long, List<ObservableObjective>> missionObjectives;
    HashMap<ObservableObjective, Integer> progress;

    public PlayerSubjectImpl(HashMap<Long, List<ObservableObjective>> missionObjectives,
                             HashMap<ObservableObjective, Integer> progress) {
        this.missionObjectives = missionObjectives;
        this.progress = progress;
    }

    public void deleteMission(Long missionId) {

        if(missionObjectives.containsKey(missionId)) {

            List<ObservableObjective> obs = missionObjectives.get(missionId);

            obs.forEach(item -> {
                if(progress.containsKey(item)) {
                    progress.remove(item);
                }
            });

            missionObjectives.remove(missionId);
        }

    }
    public List<ObservableObjective> addProgress(String target, Integer level) {

        List<Map.Entry<Long, ObservableObjective>> objectives = findObjectives(target, level);

        List<ObservableObjective> completedObjectives = new ArrayList<>();

        if (!objectives.isEmpty()) {

            Iterator<Map.Entry<Long, ObservableObjective>> iterator = objectives.iterator();

            while (iterator.hasNext()) {

                Map.Entry<Long, ObservableObjective> entry = iterator.next();
                ObservableObjective objective = entry.getValue();
                Long missionId = entry.getKey();

                Integer value = progress.merge(objective, 1, Integer::sum);

                if (value >= objective.getReqAmount()) {
                    completedObjectives.add(objective);
                    missionObjectives.get(missionId).remove(objective);
                    progress.remove(objective);
                    iterator.remove();
                }
            }
        }

        return completedObjectives;
    }

    private List<Map.Entry<Long, ObservableObjective>> findObjectives(String target, Integer level) {
        List<Map.Entry<Long, ObservableObjective>> result = new ArrayList<>();

        for (Map.Entry<Long, List<ObservableObjective>> entry : missionObjectives.entrySet()) {
            for (ObservableObjective objective : entry.getValue()) {
                if (objective.match(target, level)) {
                    result.add(new AbstractMap.SimpleEntry<>(entry.getKey(), objective));
                }
            }
        }

        return result;
    }

    public boolean isMissionCompleted(Long missionId) {
        return missionObjectives.get(missionId).isEmpty();
    }

    public void addMission(Long missionId, List<ObservableObjective> objectives) {
        missionObjectives.put(missionId, objectives);
        for(ObservableObjective ob: objectives) {
            progress.put(ob, 0);
        }
    }

    public HashMap<Long, List<ObservableObjective>> getMissionObjectives() {
        return missionObjectives;
    }

    public HashMap<ObservableObjective, Integer> getProgress() {
        return progress;
    }

    public Integer getProgressByObjective(Long objectiveId) {

        for (Map.Entry<ObservableObjective, Integer> entry : progress.entrySet()) {
            if (entry.getKey().getObjectiveId().equals(objectiveId)) {
                return entry.getValue();
            }
        }

        return null;

    }


    public Integer getCurrentProgress(ObservableObjective objective) {
        return progress.getOrDefault(objective, null);
    }

}
