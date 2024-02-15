package com.daarthy.events.app.modules.missions;

import java.util.*;

public interface PlayerSubject {

    List<ObservableObjective> addProgress(String target, Integer level);

    //Should only be used when ObservableObjective is returned.
    boolean isMissionCompleted(Long missionId);

    void addMission(Long missionId, List<ObservableObjective> objectives);

    Integer getCurrentProgress(ObservableObjective objective);

    HashMap<Long, List<ObservableObjective>> getMissionObjectives();

    HashMap<ObservableObjective, Integer> getProgress();
}
