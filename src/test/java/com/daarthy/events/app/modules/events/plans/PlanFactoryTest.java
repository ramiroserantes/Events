package com.daarthy.events.app.modules.events.plans;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlanFactoryTest {

    @Test
    public void testPlanFactoryByHunting() {
        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");
        assertNotNull(plan);
    }

    @Test
    public void testPlanFactoryByMining() {
        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");
        assertNotNull(plan);
    }

    @Test
    public void testPlanFactoryByGathering() {
        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");
        assertNotNull(plan);
    }
}
