package com.daarthy.events.model.facades.event.structure.plan;

import com.daarthy.events.model.facades.event.structure.plans.Plan;
import com.daarthy.events.model.facades.event.structure.plans.PlanFactoryImpl;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PlanTest {

    @Parameterized.Parameter(0)
    public String eventCode;

    @Parameterized.Parameter(1)
    public double expectedProbability;

    @Parameterized.Parameter(2)
    public double expectedLimit;

    @Parameterized.Parameter(3)
    public ActionType expectedActivity;

    @Parameterized.Parameters(name = "{index}: Test Plan {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"GatheringEvent", 0.5, 0.00833, ActionType.FARM},
                {"MiningEvent", 0.5, 0.002779, ActionType.MINING},
                {"HuntingEvent", 0.5, 0.11904, ActionType.KILL}
        });
    }

    @Test
    public void testPlanBasicProbability() {
        Plan plan = new PlanFactoryImpl().createEventPlan(eventCode);
        assertNotNull(plan);
        assertEquals(expectedProbability, plan.getProbability(0), 0.01);
    }

    @Test
    public void testPlanLimitProbability() {
        Plan plan = new PlanFactoryImpl().createEventPlan(eventCode);
        assertNotNull(plan);
        assertEquals(expectedLimit, plan.getLimit(), 0.0001);
    }

    @Test
    public void testPlanActivityMatch() {
        Plan plan = new PlanFactoryImpl().createEventPlan(eventCode);
        assertNotNull(plan);
        assertTrue(plan.matchActivity(expectedActivity));
    }

}
