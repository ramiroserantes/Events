package com.daarthy.events.app.modules.events.plans;

import com.daarthy.events.Events;

import java.lang.reflect.InvocationTargetException;

public class PlanFactoryImpl implements PlanFactory{

    @Override
    public Plan createEventPlan(String code) {

        StringBuilder result = new StringBuilder().append(this.getClass().getPackage().getName())
                .append(".").append(code).append("Plan");

        Plan plan = null;

        try {
            plan = (Plan) Class.forName(result.toString()).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException
                 | IllegalAccessException | NoSuchMethodException e) {
            Events.logInfo("Plan creation Error.");
        }

        return plan;

    }
}
