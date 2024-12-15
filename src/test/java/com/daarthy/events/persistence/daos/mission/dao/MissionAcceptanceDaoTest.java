package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MissionAcceptanceDaoTest {

    @Test
    public void testMissionByAccept() {

        UUID playerId = UUID.randomUUID();
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, guild.getId());

        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));
        MissionAcceptance missionAcceptance = ctx.getMissionAcceptance(mission.getId(),
                player.getPlayerId());

        MissionAcceptance found = ctx.missionAcceptanceDao().findById(missionAcceptance.getKey());
        assertEquals(missionAcceptance, found);
    }

   /* @Test
    public void testMissionAcceptanceByPlayerAccepted() {
        UUID playerId = UUID.randomUUID();
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, guild.getId());

        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));
        ctx.getMissionAcceptance(mission.getId(), player.getPlayerId());

        MySQLCriteria<Boolean> criteria = MySQLCriteria.<Boolean>builder()
                .selector(FestivalSelector.WAS_MISSION_ACCEPTED_BY_PLAYER)
                .params(List.of(mission.getId(), player.getPlayerId()))
                .resultClass(Boolean.class)
                .build();

        Boolean wasAccepted = ctx.missionAcceptanceDao().findOneByCriteria(criteria);

        assertTrue(wasAccepted);
    }

    @Test
    public void testMissionAcceptanceByPlayerNumberAccepted() {
        UUID playerId = UUID.randomUUID();
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, guild.getId());

        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));
        ctx.getMissionAcceptance(mission.getId(), player.getPlayerId());

        MySQLCriteria<Integer> criteria = MySQLCriteria.<Integer>builder()
                .selector(FestivalSelector.ACCEPTED_MISSIONS_TODAY_BY_PLAYER)
                .params(List.of(player.getPlayerId()))
                .resultClass(Integer.class)
                .build();

        Integer numberOfAccepts = ctx.missionAcceptanceDao().findOneByCriteria(criteria);

        assertEquals(1, numberOfAccepts.intValue());
    }

    @Test
    public void testMissionAcceptanceByUpdateAndPlayerRates() {
        UUID playerId = UUID.randomUUID();
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(playerId, guild.getId());

        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));
        MissionAcceptance missionAcceptance = ctx.getMissionAcceptance(mission.getId(), player.getPlayerId());

        missionAcceptance.setStatus(MissionStatus.FINALIZED);
        ctx.missionAcceptanceDao().save(missionAcceptance);

        MySQLCriteria<CompletionRate> criteria = MySQLCriteria.<CompletionRate>builder()
                .selector(FestivalSelector.FIND_PLAYER_COMPLETION_RATES)
                .params(List.of(player.getPlayerId()))
                .resultClass(CompletionRate.class)
                .build();

        List<CompletionRate> completionRates = ctx.missionAcceptanceDao().findByCriteria(criteria);

        assertEquals(1, completionRates.size());
        assertEquals(Grade.A, completionRates.get(0).getGrade());
        assertEquals(1, completionRates.get(0).getTotalMissions(), 0.0);
        assertEquals(1, completionRates.get(0).getCompletedMissions(), 0.0);
    }*/
    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private final DaoContext ctx = new DaoContext();

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
