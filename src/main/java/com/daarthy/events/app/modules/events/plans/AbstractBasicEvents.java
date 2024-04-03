package com.daarthy.events.app.modules.events.plans;

public abstract class AbstractBasicEvents extends AbstractPlan{

    protected final double limit;
    protected final double coefficient;

    protected AbstractBasicEvents(double limit, double coefficient) {
        super();
        this.limit = limit;
        this.coefficient = coefficient;
    }

    @Override
    protected double getTemplatedProbability(int earnedAmount) {
        return Math.max(getBasicFormulaValue(earnedAmount), limit);
    }

    @Override
    protected double getBasicFormulaValue(int earnedAmount) {
        return (0.5 - (coefficient * earnedAmount)) / 100;
    }

    @Override
    public double getLimit() {
        return limit * 100;
    }

    @Override
    public double getCoefficient() {
        return coefficient ;
    }
}
