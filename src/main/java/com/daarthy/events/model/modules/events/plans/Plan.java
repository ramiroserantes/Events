package com.daarthy.events.model.modules.events.plans;


import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

public interface Plan {

    boolean applyPlan(int earnedAmount);

    double getProbability(int earnedAmount);

    double getLimit();

    double getCoefficient();

    boolean matchActivity(ActionType actionType);

}
