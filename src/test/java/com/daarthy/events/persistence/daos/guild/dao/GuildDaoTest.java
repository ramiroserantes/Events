package com.daarthy.events.persistence.daos.guild.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import org.junit.After;

public class GuildDaoTest {

   /* @Test
    public void testGuildByCreateAndDelete() {

        Guild createdGuild = ctx.getGuild(2L);

        Guild foundGuild = ctx.guildDao().findById(createdGuild.getId());
        assertEquals(createdGuild, foundGuild);

        ctx.guildDao().deleteById(createdGuild.getId());
        Guild afterDeletion = ctx.guildDao().findById(createdGuild.getId());

        assertNull(afterDeletion);
    }

   /* @Test
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

    }*/

    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private DaoContext ctx = new DaoContext();

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
