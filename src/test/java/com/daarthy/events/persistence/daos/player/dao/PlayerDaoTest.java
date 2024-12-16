package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

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
    // Player Selectors
    // *****************************************************
    @Test
    public void testPlayerSelectorByFindGuildByPlayer() {

        UUID playerId = UUID.randomUUID();
        ctx.getPlayer(playerId, DEFAULT_GUILD);

        MiniCriteria<Guild> criteria = PostgresCriteria.<Guild>builder()
                .selector(FestivalSelector.FIND_GUILD_BY_PLAYER_ID)
                .params(List.of(playerId))
                .resultClass(Guild.class)
                .build();

        Guild foundGuild = ctx.searchDao().findOneByCriteria(criteria);

        assertNotNull(foundGuild);
        assertEquals(1L, foundGuild.getId(), 0.0);
    }

    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private static final Long DEFAULT_GUILD = 1L;
    private PersistenceTestContext ctx = new PersistenceTestContext();

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }

}
