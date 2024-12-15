package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ObjectiveDaoTest {

    @Test
    public void testObjectiveDaoByCreation() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(2));

        Objective objective = ctx.getObjective(mission.getId());
        Objective found = ctx.objectiveDao().findById(objective.getId());

        assertEquals(found, objective);
    }
    /*@Test
    public void testObjectiveDaoFindByMission() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(2));

        Objective objective = ctx.getObjective(mission.getId());

        MySQLCriteria<Objective> criteria = MySQLCriteria.<Objective>builder()
                .selector(FestivalSelector.FIND_MISSION_OBJECTIVES)
                .params(List.of(mission.getId()))
                .resultClass(Objective.class)
                .build();

        List<Objective> objectivesPerMission = ctx.objectiveDao().findByCriteria(criteria);

        assertEquals(1, objectivesPerMission.size());
        assertEquals(objective, objectivesPerMission.get(0));
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
