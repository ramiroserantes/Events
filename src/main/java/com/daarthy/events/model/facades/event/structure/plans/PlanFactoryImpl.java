package com.daarthy.events.model.facades.event.structure.plans;

import com.daarthy.events.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class PlanFactoryImpl implements PlanFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanFactoryImpl.class);

    @Override
    public Plan createEventPlan(String code) {

        StringBuilder result = new StringBuilder().append(this.getClass().getPackage().getName())
                .append(".").append(code).append("Plan");
        Plan plan = null;

        try {
            plan = (Plan) Class.forName(result.toString()).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException
                 | IllegalAccessException | NoSuchMethodException e) {
            LOGGER.error(Events.MICRO_NAME + "- Error creating the event with the code: {}",
                    code);
        }

        return plan;

    }
}
