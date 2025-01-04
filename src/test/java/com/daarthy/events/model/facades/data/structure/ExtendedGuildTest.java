package com.daarthy.events.model.facades.data.structure;

import com.daarthy.events.persistence.daos.guild.entities.Guild;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExtendedGuildTest {
    private Guild guild;
    private ExtendedGuild extendedGuild;

    @Before
    public void setUp() {
        guild = Guild.builder()
                .lvl(5)
                .experience(20000F)
                .maxLvl(10)
                .levelUpMod(0.1F)
                .build();

        extendedGuild = GuildAdapter.create(guild);
    }

    @Test
    public void testToStorageGuild() {
        extendedGuild.addExperience(30000F);
        Guild result = extendedGuild.toStorageGuild();

        assertEquals(5, result.getLvl(), 0);
        assertEquals(50000F, result.getExperience(), .0);
        assertEquals(55, extendedGuild.getMaxJobLevel());
    }

    @Test
    public void testSetLevelUpMod() {
        extendedGuild.setLevelUpMod(0.2F);
        float expectedRequiredExp = 50000 * (1 + 5 * 4) * (1 - 0.2F);
        assertEquals(expectedRequiredExp, extendedGuild.getRequiredExp(), .01F);
    }

    @Test
    public void testAddExperienceLevelUp() {
        extendedGuild.addExperience(30000000F);
        Guild storage = extendedGuild.toStorageGuild();

        assertEquals(10, storage.getLvl(), 0);
        assertEquals(0F, storage.getExperience(), .01F);
    }

    @Test
    public void testAddExperienceNoLevelUp() {
        extendedGuild.addExperience(10000F);
        Guild storage = extendedGuild.toStorageGuild();

        assertEquals(5, storage.getLvl(), 0);
        assertEquals(30000F, storage.getExperience(), .01F);
    }

    @Test
    public void testRemoveExperienceLevelDown() {
        extendedGuild.removeExperience(25000F);
        Guild storage = extendedGuild.toStorageGuild();

        assertEquals(4, storage.getLvl(), 0);
        assertTrue(storage.getExperience() > 0);
    }

    @Test
    public void testRemoveExperienceNoLevelDown() {
        extendedGuild.removeExperience(10000F);
        Guild storage = extendedGuild.toStorageGuild();

        assertEquals(5, storage.getLvl(), 0);
        assertEquals(10000F, storage.getExperience(), .01F);
    }

    @Test
    public void testComputeRequiredExp() {
        float expectedRequiredExp = 50000 * (1 + 5 * 4) * (1 - guild.getLevelUpMod());
        assertEquals(expectedRequiredExp, extendedGuild.getRequiredExp(), .01F);
    }

    @Test
    public void testGetMaxJobLevel() {
        int expectedMaxJobLevel = 15 + 5 * 8;
        assertEquals(expectedMaxJobLevel, extendedGuild.getMaxJobLevel());
    }
}
