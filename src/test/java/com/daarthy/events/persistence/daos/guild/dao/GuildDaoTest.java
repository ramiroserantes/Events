package com.daarthy.events.persistence.daos.guild.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GuildDaoTest {

    private final PersistenceTestContext ctx = new PersistenceTestContext();

    @Test
    public void testGuildByCreateAndDelete() {

        Guild createdGuild = ctx.getGuild(2L);

        Guild foundGuild = ctx.guildDao().findById(createdGuild.getId());
        assertEquals(createdGuild, foundGuild);

        ctx.guildDao().deleteById(createdGuild.getId());
        Guild afterDeletion = ctx.guildDao().findById(createdGuild.getId());

        assertNull(afterDeletion);
    }

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
