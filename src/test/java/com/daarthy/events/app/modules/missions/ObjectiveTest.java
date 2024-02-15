package com.daarthy.events.app.modules.missions;

import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectiveTest {

    private static final Long objectiveId1 = 1L;

    @Test
    public void testObjectiveByTrueMatch() {

        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);

        assertTrue(objective.match("ZOMBIE", 12));

    }

    @Test
    public void testObjectiveByFalseMatch() {

        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);

        assertFalse(objective.match("ZOMBIE", 2));

    }

    @Test
    public void testObjectiveByObserved() {

        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);

        objective.updateObserved(1);

        assertTrue(objective.isObserved());

    }

    @Test
    public void testObjectiveByNotObserved() {

        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);

        objective.updateObserved(-1);

        assertFalse(objective.isObserved());

    }
}
