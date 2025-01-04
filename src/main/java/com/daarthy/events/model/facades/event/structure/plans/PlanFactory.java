package com.daarthy.events.model.facades.event.structure.plans;

public interface PlanFactory {

    /**
     * Creates a specific implementation of a {@link Plan} based on the provided code.
     *
     * @param code the unique identifier for the plan type.
     * @return an instance of the {@link Plan} corresponding to the given code, or null if creation fails.
     */
    Plan createEventPlan(String code);

}
