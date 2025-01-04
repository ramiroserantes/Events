package com.daarthy.events.model.facades.event.structure.plans;

import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlan implements Plan {

    private final List<ActionType> activities = new ArrayList<>();
    private final SecureRandom random = new SecureRandom();

    protected AbstractPlan() {
        setUpActivities(activities);
    }

    @Override
    public boolean applyPlan(int earnedAmount) {
        double value = random.nextDouble();
        value = Math.round(value * 1e8) / 1e8;
        return value < getTemplatedProbability(earnedAmount);
    }

    @Override
    public double getProbability(int earnedAmount) {
        return getTemplatedProbability(earnedAmount) * 100;
    }

    @Override
    public boolean matchActivity(ActionType actionType) {
        return activities.contains(actionType);
    }

    protected abstract void setUpActivities(List<ActionType> activities);

    protected abstract double getTemplatedProbability(int earnedAmount);

    protected abstract double getBasicFormulaValue(int earnedAmount);
}
