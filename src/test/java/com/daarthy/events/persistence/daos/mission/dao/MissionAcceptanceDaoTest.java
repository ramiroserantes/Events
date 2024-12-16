package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.CompletionRate;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import com.daarthy.mini.shared.classes.enums.festivals.MissionStatus;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MissionAcceptanceDaoTest {

    @Test
    public void testMissionByAccept() {

        UUID playerId = UUID.randomUUID();
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, guild.getId());

        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));
        MissionAcceptance missionAcceptance = ctx.getMissionAcceptance(mission.getId(),
                player.getPlayerId(), LocalDate.now());

        MissionAcceptance found = ctx.missionAcceptanceDao().findById(missionAcceptance.getKey());
        assertEquals(missionAcceptance, found);
    }

    // *****************************************************
    // MissionAcceptanceDao Selectors
    // *****************************************************
    @Test
    public void testMissionAcceptanceSelectorByWasAcceptedByPlayer() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(1));
        ctx.getMissionAcceptance(mission.getId(), playerId, LocalDate.now().plusDays(1));

        MiniCriteria<Boolean> criteria = PostgresCriteria.<Boolean>builder()
                .selector(FestivalSelector.WAS_MISSION_ACCEPTED_BY_PLAYER)
                .params(List.of(mission.getId(), playerId))
                .resultClass(Boolean.class)
                .build();

        Boolean missionsWasAcceptedByPlayer = ctx.searchDao().findOneByCriteria(criteria);

        assertTrue(missionsWasAcceptedByPlayer);
    }

    @Test
    public void testMissionAcceptanceSelectorByAcceptedMissionsTodayWithFailingPastMissions() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);

        Mission mission1 = ctx.getMission(2L, LocalDate.now().plusDays(1));
        Mission mission2 = ctx.getMission(2L, LocalDate.now().plusDays(1));
        Mission mission3 = ctx.getMission(2L, LocalDate.now().minusDays(1));

        ctx.getMissionAcceptance(mission1.getId(), playerId, LocalDate.now());
        ctx.getMissionAcceptance(mission2.getId(), playerId, LocalDate.now());
        ctx.getMissionAcceptance(mission3.getId(), playerId, LocalDate.now().minusDays(1));

        MiniCriteria<Void> failureCriteria = PostgresCriteria.<Void>builder()
                .selector(FestivalSelector.FAIL_PAST_MISSIONS)
                .build();

        ctx.searchDao().querySelectorExecutor(failureCriteria);
        MiniCriteria<Integer> criteria = PostgresCriteria.<Integer>builder()
                .selector(FestivalSelector.ACCEPTED_MISSIONS_TODAY_BY_PLAYER)
                .params(List.of(playerId))
                .resultClass(Integer.class)
                .build();

        Integer missionsAcceptedByPlayerToday = ctx.searchDao().findOneByCriteria(criteria);

        assertEquals(2, missionsAcceptedByPlayerToday, 0.0);
    }

    @Test
    public void testMissionAcceptanceSelectorByPlayerRates() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);

        Mission mission1 = ctx.getMission(2L, LocalDate.now().plusDays(1));
        Mission mission2 = ctx.getMission(2L, LocalDate.now().plusDays(1));
        Mission mission3 = ctx.getMission(2L, LocalDate.now().minusDays(1));

        ctx.getMissionAcceptance(mission1.getId(), playerId, LocalDate.now());
        MissionAcceptance missionAcceptance = ctx.getMissionAcceptance(mission2.getId(), playerId, LocalDate.now());
        ctx.getMissionAcceptance(mission3.getId(), playerId, LocalDate.now().minusDays(1));

        MiniCriteria<Void> failureCriteria = PostgresCriteria.<Void>builder()
                .selector(FestivalSelector.FAIL_PAST_MISSIONS)
                .build();

        ctx.searchDao().querySelectorExecutor(failureCriteria);
        missionAcceptance.setStatus(MissionStatus.FINALIZED);
        mission3.setGrade(Grade.S);
        ctx.missionAcceptanceDao().save(missionAcceptance);
        ctx.missionDao().save(mission3);

        MiniCriteria<CompletionRate> criteria = PostgresCriteria.<CompletionRate>builder()
                .selector(FestivalSelector.FIND_PLAYER_COMPLETION_RATES)
                .params(List.of(playerId))
                .resultClass(CompletionRate.class)
                .build();

        List<CompletionRate> missionsCompletedByPlayer = ctx.searchDao().findByCriteria(criteria);

        assertEquals(2, missionsCompletedByPlayer.size());

        // First item Group
        assertEquals(Grade.A, missionsCompletedByPlayer.get(0).getGrade());
        assertEquals(2, missionsCompletedByPlayer.get(0).getTotalMissions(), 0.0);
        assertEquals(1, missionsCompletedByPlayer.get(0).getCompletedMissions(), 0.0);

        // Second item Group
        assertEquals(Grade.S, missionsCompletedByPlayer.get(1).getGrade());
        assertEquals(1, missionsCompletedByPlayer.get(1).getTotalMissions(), 0.0);
        assertEquals(0, missionsCompletedByPlayer.get(1).getCompletedMissions(), 0.0);
    }

    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private final PersistenceTestContext ctx = new PersistenceTestContext();

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
