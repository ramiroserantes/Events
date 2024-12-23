package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class MissionDaoTest {

    private final PersistenceTestContext ctx = new PersistenceTestContext();

    @Test
    public void testMissionByCreation() {

        Guild guild = ctx.getGuild(3L);
        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));

        Mission foundMission = ctx.missionDao().findById(mission.getId());

        assertEquals(mission, foundMission);
    }

    // *****************************************************
    // MissionDao Selectors
    // *****************************************************
    @Test
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
    }

    @Test
    public void testMissionSelectorByPlayerMissionsAccepted() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(1));
        ctx.getMissionAcceptance(mission.getId(), playerId, LocalDate.now().plusDays(1));

        MiniCriteria<Mission> criteria = PostgresCriteria.<Mission>builder()
                .selector(FestivalSelector.FIND_CURRENT_PLAYER_MISSIONS)
                .params(List.of(playerId))
                .resultClass(Mission.class)
                .build();

        List<Mission> missionsAcceptedByPlayer = ctx.searchDao().findByCriteria(criteria);

        assertFalse(missionsAcceptedByPlayer.isEmpty());
        assertEquals(mission, missionsAcceptedByPlayer.get(0));
    }

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }

}
