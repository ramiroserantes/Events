package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerDaoTest {

    @Test
    public void testByCreateAndFindPlayer() {

        UUID playerId = UUID.randomUUID();
        EventsPlayer player = ctx.getPlayer(playerId, 1L);

        EventsPlayer found = ctx.playerDao().findById(playerId);
        assertEquals(player, found);
    }

    @Test
    public void testByCreateAndUpdatePlayer() {

        UUID playerId = UUID.randomUUID();
        EventsPlayer player = ctx.getPlayer(playerId, 1L);
        player.setMaxMissions(1000);

        EventsPlayer firstFound = ctx.playerDao().findById(playerId);
        assertNotEquals(player, firstFound);

        ctx.playerDao().save(player);
        EventsPlayer secondFound = ctx.playerDao().findById(playerId);
        assertEquals(player, secondFound);
    }

    @Test
    public void testPlayerBySaveAll() {

        ctx.getGuild(2L);
        ctx.getGuild(3L);

        UUID player1 = UUID.randomUUID();
        UUID player2 = UUID.randomUUID();

        EventsPlayer eventsPlayer1 = ctx.getPlayer(player1, 1L);
        EventsPlayer eventsPlayer2 = ctx.getPlayer(player2, 2L);

        eventsPlayer1.setMaxMissions(7);
        eventsPlayer2.setMaxMissions(2);

        ctx.playerDao().saveAll(List.of(eventsPlayer1, eventsPlayer2));

        assertEquals(7, ctx.playerDao().findById(player1).getMaxMissions());
        assertEquals(2, ctx.playerDao().findById(player2).getMaxMissions());
    }

    @Test
    public void testPlayerByRemoveFromGuild() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);

        ctx.getPlayer(playerId, 2L);
        ctx.guildDao().deleteById(2L);

        EventsPlayer foundPlayerOnTrigger = ctx.playerDao().findById(playerId);

        assertEquals(1L, foundPlayerOnTrigger.getGuildId(), 0.0);
    }

    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private DaoContext ctx = new DaoContext();
    @After
    public void cleanUp() {
        ctx.cleanUp();
    }

}
