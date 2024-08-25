package com.daarthy.events.persistence.daos.guild.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MySQLCriteria;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GuildDaoTest {

    @Test
    public void testGuildByCreateAndDelete() {

        Guild createdGuild = ctx.getGuild(2L);

        Guild foundGuild = ctx.guildDao().findById(createdGuild.getId());
        assertEquals(createdGuild, foundGuild);

        ctx.guildDao().deleteById(createdGuild.getId());
        Guild afterDeletion = ctx.guildDao().findById(createdGuild.getId());

        assertNull(afterDeletion);
    }

    @Test
    public void testGuildByCreateAndDeleteAll() {

        Guild createdGuild_1 = ctx.getGuild(2L);
        Guild createdGuild_2 = ctx.getGuild(3L);

        ctx.guildDao().deleteAll(List.of(createdGuild_1, createdGuild_2));

        Guild afterDeletion_1 = ctx.guildDao().findById(createdGuild_1.getId());
        Guild afterDeletion_2 = ctx.guildDao().findById(createdGuild_2.getId());

        assertNull(afterDeletion_1);
        assertNull(afterDeletion_2);
    }

    @Test
    public void testGuildByFindByPlayer() {

        UUID playerId = UUID.randomUUID();

        Guild createdGuild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, createdGuild.getId());

        MySQLCriteria<Guild> selector = MySQLCriteria.<Guild>builder()
                .selector(FestivalSelector.FIND_GUILD_BY_PLAYER_ID)
                .params(List.of(player.getPlayerId()))
                .resultClass(Guild.class)
                .build();

        Guild found = ctx.guildDao().findOneByCriteria(selector);

        assertEquals(found, createdGuild);

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
