package com.daarthy.events.model.facades.event.structure.plan;

import com.daarthy.events.model.facades.event.structure.plans.Plan;
import com.daarthy.events.model.facades.event.structure.plans.PlanFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class PlanFactoryTest {

    @Parameterized.Parameter(0)
    public String eventCode;

    @Parameterized.Parameters(name = "Test PlanFactory with eventCode: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"HuntingEvent"},
                {"MiningEvent"},
                {"GatheringEvent"}
        });
    }

    @Test
    public void testPlanFactory() {
        Plan plan = new PlanFactoryImpl().createEventPlan(eventCode);
        assertNotNull("Plan should not be null for eventCode: " + eventCode, plan);
    }
}
