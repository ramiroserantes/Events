package com.daarthy.events.model.facades.event.structure.plans;


import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

public interface Plan {

    /**
     * Applies the plan logic to determine if an event is successful based on the earned amount.
     *
     * @param earnedAmount the amount earned or accumulated to evaluate against the plan.
     * @return true if the plan is applied successfully, false otherwise.
     */
    boolean applyPlan(int earnedAmount);

    /**
     * Calculates the probability associated with the plan based on the earned amount.
     *
     * @param earnedAmount the amount earned or accumulated.
     * @return the probability as a percentage.
     */
    double getProbability(int earnedAmount);

    /**
     * Retrieves the limit value defined for the plan.
     *
     * @return the limit value.
     */
    double getLimit();

    /**
     * Retrieves the coefficient used in the plan calculations.
     *
     * @return the coefficient value.
     */
    double getCoefficient();

    /**
     * Determines if a given activity matches any predefined activity in the plan.
     *
     * @param actionType the type of activity to match.
     * @return true if the activity is part of the plan, false otherwise.
     */
    boolean matchActivity(ActionType actionType);

}
