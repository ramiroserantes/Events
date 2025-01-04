package com.daarthy.events.model.facades.event.structure.plan;

import com.daarthy.events.model.facades.event.structure.plans.Plan;
import com.daarthy.events.model.facades.event.structure.plans.PlanFactoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlanStudyTest {

    @Test
    public void testPlanByGatheringStudy() {
        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");
        runStudyTest(plan, "GatheringEvent");
    }

    @Test
    public void testPlanByMiningStudy() {
        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");
        runStudyTest(plan, "MiningEvent");
    }

    @Test
    public void testPlanByHuntingStudy() {
        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");
        runStudyTest(plan, "HuntingEvent");
    }

    private void runStudyTest(Plan plan, String eventName) {
        int samples = 0;
        int earnedAmount = 0;
        boolean match = false;
        int totalSamples = 0;
        int totalSuccesses = 0;

        System.out.printf("----------------------%s-------------------------------\n", eventName);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|  Current Success  |  Samples  |  Avg. Samples |  Total Samples |");
        System.out.println("-------------------------------------------------------------------");

        for (int success = 0; success < 220; success++) {
            match = false;
            while (!match) {
                samples++;
                totalSamples++;
                if (plan.applyPlan(earnedAmount)) {
                    earnedAmount++;
                    totalSuccesses++;
                    if ((earnedAmount >= 1 && earnedAmount <= 4) ||
                            (earnedAmount >= 88 && earnedAmount <= 92) ||
                            (earnedAmount >= 198 && earnedAmount <= 202)) {
                        printTable(samples, earnedAmount, totalSamples, totalSuccesses);
                    }
                    match = true;
                    samples = 0;
                }
            }
        }
        assertTrue(match);
    }

    private void printTable(int samples, int earnedAmount, int totalSamples, int totalSuccesses) {
        double averageSamples = (double) totalSamples / totalSuccesses;
        System.out.printf("|%10d         |  %6d  |  %12.2f |  %13d  |\n", earnedAmount, samples, averageSamples,
                totalSamples);
    }
}
