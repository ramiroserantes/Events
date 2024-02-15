package com.daarthy.events.app.modules.missions;

public interface ObservableObjective {

    boolean isObserved();

    void updateObserved(int amount);

    boolean match(String target, Integer level);

    int getReqAmount();

    Long getObjectiveId();
}
