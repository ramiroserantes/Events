package com.daarthy.events.app.modules.guilds;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    @Test
    public void testLevelByDecrease() {

        Level level = new Level(100F, 0, 1, 0F);

        level.removeExperience(150F);

        assertEquals(-50.0, level.getCurrentExp(), 0.0);
    }

    @Test
    public void testLevelByRequirements() {

        Level level = new Level(100F, 6, 7, 0F);

        assertEquals(1250000, level.getRequiredExp(), 0.0);
    }

    @Test
    public void testLevelByLevelUpMod() {

        Level level = new Level(100F, 6, 7, 0F);

        level.setLevelUpMod(0.1F);

        assertEquals(1125000, level.getRequiredExp(), 0.0);
        assertEquals(0.1F, level.getLevelUpMod(), 0.0);
    }

    @Test
    public void testLevelByRequirementsPolicy() {

        Level level = new Level(100F, 6, 7, 0.1F);

        assertEquals(1125000, level.getRequiredExp(), 0.0);
    }

    @Test
    public void testLevelByRequirementsIncreaseLevel() {

        Level level = new Level(100F, 5, 6, 0.1F);

        level.addExperience(10000000F);

        assertEquals(6, level.getCurrentLevel());
        assertEquals(0F, level.getCurrentExp(), 0.0);
    }
}
