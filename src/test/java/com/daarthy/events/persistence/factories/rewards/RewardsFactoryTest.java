package com.daarthy.events.persistence.factories.rewards;

import com.daarthy.mini.shared.classes.economy.MiniReward;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class RewardsFactoryTest {

    private final Grade grade;
    private final RewardsFactory rewardsFactory = new RewardsFactoryImpl();

    public RewardsFactoryTest(Grade grade) {
        this.grade = grade;
    }

    @Parameterized.Parameters(name = "Testing rewards for Grade: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Grade.D},
                {Grade.E},
                {Grade.F}
        });
    }

    @Test
    public void testRewardsByDGrade() {
        MiniReward miniReward = rewardsFactory.getReward(grade);

        assertNotNull(miniReward.getCoins());
        assertNotNull(miniReward.getExp());
        assertNotNull(miniReward.getFood());
        assertNotNull(miniReward.getMinerals());
        assertNotNull(miniReward.getLeather());
    }
}
