package com.daarthy.events.model.modules.guilds;

public class EventMedalsTest {


   /* private Guild getGuild() {
        return new Guild("kName", LocalDateTime.now(), new Level(0F,0,0,0F),
                new GuildModifiers(0, 0F));
    }

    @Test
    public void testAddMedals() {

        EventMedals eventMedals = new EventMedals();

        for(int i = 0; i < 100; i++) {
            eventMedals.addMedals(1L);
        }

        assertEquals(100, eventMedals.getEventMedals(1L), 0.0);

    }

    @Test
    public void testGuildByRemoveMedalsNull() {

        EventMedals eventMedals = new EventMedals();

        assertFalse(eventMedals.removeMedals(1L, 900));

    }

    @Test
    public void testGuildByRemoveMedals() {

        Guild guild = getGuild();
        guild.getGuildModifiers().setAmpMissions(90);

        EventMedals eventMedals = new EventMedals();

        for(int i = 0; i < 100; i++) {
            eventMedals.addMedals(1L);
        }

        assertTrue(eventMedals.removeMedals(1L, 50));
        assertEquals(90, guild.getGuildModifiers().getAmpMissions());

    }

    @Test
    public void testEventMedalsByMap() {

        EventMedals eventMedals = new EventMedals();

        for(int i = 0; i < 100; i++) {
            eventMedals.addMedals(1L);
        }

        assertEquals(1, eventMedals.getMedals().size());

    }*/
}
