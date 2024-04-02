package com.daarthy.events.app.modules.guilds;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventMedalsTest {


    private Guild getGuild() {
        return new Guild("kName", LocalDateTime.now(), new Level(0F,0,0,0F),
                new EventMedals(), new GuildModifiers(0, 0F));
    }

    @Test
    public void testGuildByAddMedals() {

        Guild guild = getGuild();

        for(int i = 0; i < 100; i++) {
            guild.getEventMedals().addMedals(1L);
        }

        assertEquals(100, guild.getEventMedals().getEventMedals(1L), 0.0);

    }

    @Test
    public void testGuildByRemoveMedalsNull() {

        Guild guild = getGuild();

        assertFalse(guild.getEventMedals().removeMedals(1L, 900));

    }

    @Test
    public void testGuildByRemoveMedals() {

        Guild guild = getGuild();
        guild.getGuildModifiers().setAmpMissions(90);

        for(int i = 0; i < 100; i++) {
            guild.getEventMedals().addMedals(1L);
        }


        assertTrue(guild.getEventMedals().removeMedals(1L, 50));
        assertEquals(90, guild.getGuildModifiers().getAmpMissions());

    }

    @Test
    public void testGuildByMap() {

        Guild guild = getGuild();


        for(int i = 0; i < 100; i++) {
            guild.getEventMedals().addMedals(1L);
        }


        assertEquals(1, guild.getEventMedals().getMedals().size());

    }
}
