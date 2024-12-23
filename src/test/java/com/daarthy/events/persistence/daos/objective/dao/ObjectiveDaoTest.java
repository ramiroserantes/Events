package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ObjectiveDaoTest {

    private final PersistenceTestContext ctx = new PersistenceTestContext();

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

    // *****************************************************
    // ObjectiveDao Selectors
    // *****************************************************
    @Test
    public void testObjectiveDaoFindByMissionCriteria() {

        UUID playerId = UUID.randomUUID();
        ctx.getGuild(2L);
        ctx.getPlayer(playerId, 2L);
        Mission mission = ctx.getMission(2L, LocalDate.now().plusDays(2));

        Objective objective = ctx.getObjective(mission.getId());

        PostgresCriteria<Objective> criteria = PostgresCriteria.<Objective>builder()
                .selector(FestivalSelector.FIND_MISSION_OBJECTIVES)
                .params(List.of(mission.getId()))
                .resultClass(Objective.class)
                .build();

        List<Objective> objectivesPerMission = ctx.searchDao().findByCriteria(criteria);

        assertEquals(1, objectivesPerMission.size());
        assertEquals(objective, objectivesPerMission.get(0));
    }

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }

}
