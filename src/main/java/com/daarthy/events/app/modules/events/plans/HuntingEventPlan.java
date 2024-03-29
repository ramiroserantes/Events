package com.daarthy.events.app.modules.events.plans;

import com.daarthy.events.persistence.missionDao.ActionType;

import java.util.List;

public class HuntingEventPlan extends AbstractPlan {

    private static final double limit = 0.0011904;

    public HuntingEventPlan() {
        super();
    }

    @Override
    protected double getTemplatedProbability(int earnedAmount) {
        return Math.max(getBasicFormulaValue(earnedAmount), limit);
    }

    @Override
    protected double getBasicFormulaValue(int earnedAmount) {
        return (0.5 - 0.0042328 * earnedAmount) / 100;
    }

    @Override
    public double getLimit() {
        return limit * 100;
    }

    @Override
    protected void setUpActivities(List<ActionType> activities) {
        activities.add(ActionType.KILL);
    }
}


