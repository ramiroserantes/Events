package com.daarthy.events.persistence.daos;

import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SearchDaoTest {

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
    // MissionDao Selectors
    // *****************************************************
   /* @Test
    public void testMissionSelectorByGuildMissionsToday() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(1));

        MiniCriteria<Mission> criteria = PostgresCriteria.<Mission>builder()
                .selector(FestivalSelector.GUILD_MISSIONS_TODAY)
                .params(List.of(2L))
                .resultClass(Mission.class)
                .build();

        List<Mission> missionsList = ctx.searchDao().findByCriteria(criteria);

        assertFalse(missionsList.isEmpty());
        assertTrue(missionsList.contains(mission));
        assertEquals(0, missionsList.get(0).getCurrentPlayers(), 0.0);
    }*/

    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private final DaoContext ctx = new DaoContext();
    private static final Long DEFAULT_GUILD = 1L;

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
