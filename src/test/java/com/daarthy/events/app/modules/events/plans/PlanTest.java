package com.daarthy.events.app.modules.events.plans;

import com.daarthy.events.persistence.missionDao.ActionType;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlanTest {

    @Test
    public void testPlanByGatheringBasicProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");

        assertEquals(0.5, plan.getProbability(0), 0.01);
    }

    @Test
    public void testPlanByMiningBasicProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        assertEquals(0.5, plan.getProbability(0), 0.01);
    }

    @Test
    public void testPlanByHuntingBasicProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        assertEquals(0.5, plan.getProbability(0), 0.01);
    }

    @Test
    public void testPlanByGatheringBeforeLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");


        assertEquals(0.03565, plan.getProbability(85), 0.01);
    }

    @Test
    public void testPlanByMiningBeforeLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        assertEquals(0.03040, plan.getProbability(85), 0.01);
    }

    @Test
    public void testPlanByHuntingBeforeLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        assertEquals(0.1402, plan.getProbability(85), 0.01);
    }

    @Test
    public void testPlanByGatheringBeyondLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");


        assertEquals(0.00833, plan.getProbability(98), 0.0001);
    }

    @Test
    public void testPlanByMiningLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        assertEquals(0.002779, plan.getLimit(), 0.0001);
    }

    @Test
    public void testPlanByHuntingLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        assertEquals(0.11904, plan.getLimit(), 0.0001);
    }

    @Test
    public void testPlanByGatheringLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");


        assertEquals(0.00833, plan.getLimit(), 0.0001);
    }

    @Test
    public void testPlanByMiningBeyondLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        assertEquals(0.002779, plan.getProbability(95), 0.00001);
    }

    @Test
    public void testPlanByHuntingBeyondLimitProbability() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        assertEquals(0.11904, plan.getProbability(95), 0.0001);
    }

    @Test
    public void testPlanByGatheringMatch() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");

        assertTrue(plan.matchActivity(ActionType.FARM));
    }

    @Test
    public void testPlanByMiningMatch() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        assertTrue(plan.matchActivity(ActionType.MINING));
    }

    @Test
    public void testPlanByHuntingMatch() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        assertTrue(plan.matchActivity(ActionType.KILL));
    }


    @Test
    public void testPlanByGatheringStudy() {

        Plan plan = new PlanFactoryImpl().createEventPlan("GatheringEvent");

        int samples = 0;
        int earnedAmount = 0;
        boolean match = false;
        int totalSamples = 0;
        int totalSuccesses = 0;

        System.out.println("----------------------GatheringEvent-------------------------------");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|  Current Success  |  Samples  |  Avg. Samples |  Total Samples |");
        System.out.println("-------------------------------------------------------------------");

        for(int success = 0; success < 220; success++) {
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

    @Test
    public void testPlanByHuntingStudy() {

        Plan plan = new PlanFactoryImpl().createEventPlan("HuntingEvent");

        int samples = 0;
        int earnedAmount = 0;
        boolean match = false;
        int totalSamples = 0;
        int totalSuccesses = 0;

        System.out.println("----------------------HuntingEvent-------------------------------");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|  Current Success  |  Samples  |  Avg. Samples |  Total Samples |");
        System.out.println("-------------------------------------------------------------------");

        for(int success = 0; success < 220; success++) {
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

    @Test
    public void testPlanByMiningStudy() {

        Plan plan = new PlanFactoryImpl().createEventPlan("MiningEvent");

        int samples = 0;
        int earnedAmount = 0;
        boolean match = false;
        int totalSamples = 0;
        int totalSuccesses = 0;

        System.out.println("----------------------MiningEvent-------------------------------");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("|  Current Success  |  Samples  |  Avg. Samples |  Total Samples |");
        System.out.println("-------------------------------------------------------------------");

        for(int success = 0; success < 220; success++) {
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
        System.out.printf("|%10d         |  %6d  |  %12.2f |  %13d  |\n", earnedAmount, samples, averageSamples, totalSamples);
    }
}
