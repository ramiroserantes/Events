package com.daarthy.events.app.modules.missions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Objective implements ObservableObjective {

    private Long objectiveId;
    private String target;
    private Integer level;
    private int reqAmount;

    private int observers;

    public Objective(Long objectiveId, String target, Integer level, int reqAmount) {
        this.objectiveId = objectiveId;
        this.target = target;
        this.level = level;
        this.reqAmount = reqAmount;
        this.observers = 1;
    }

    public boolean isObserved() {
        return observers > 0;
    }

    public synchronized void updateObserved(int amount) {
        this.observers += amount;
    }

    public boolean match(String target, Integer level) {
        return target.equalsIgnoreCase(this.target) && (level == null ||
                level >= this.level);
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public Long getObjectiveId() {
        return objectiveId;
    }
}
