package com.daarthy.events.app.modules.events.plans;

import com.daarthy.events.persistence.missionDao.ActionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractPlan implements Plan {

    private List<ActionType> activities = new ArrayList<>();

    public AbstractPlan() {
        setUpActivities(activities);
    }

    public boolean applyPlan(int earnedAmount) {
        Random random = new Random();
        double value = random.nextDouble();
        value = Math.round(value * 1e8) / 1e8;
        return value < getTemplatedProbability(earnedAmount);
    }

    public double getProbability(int earnedAmount) {
        return getTemplatedProbability(earnedAmount) * 100;
    }


    @Override
    public boolean matchActivity(ActionType actionType) {

        for(ActionType action : activities) {
            if(action.equals(actionType)) {
                return true;
            }
        }
        return false;

    }

    protected abstract void setUpActivities(List<ActionType> activities);

    protected abstract double getTemplatedProbability(int earnedAmount);

    protected abstract double getBasicFormulaValue(int earnedAmount);
}
