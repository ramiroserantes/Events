package com.daarthy.events.app.modules.missions;


import com.daarthy.events.persistence.daos.mission.Grade;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.*;

public class PlayerMissionsTest {

    private static final Long MISSION_1 = 1L;
    private static final Long MISSION_2 = 2L;

    private static final Long OB1 = 1L;
    private static final Long OB2 = 2L;
    private static final Long OB3 = 3L;

    private Objective getObjective(Long missionId, String target, Integer level) {
        return new Objective(missionId, LocalDate.now().plusDays(1L), Grade.A, target, level, 6);
    }

    private Objective getObjective(Long missionId, String target, Integer level, int req) {
        return new Objective(missionId, LocalDate.now().plusDays(1L), Grade.A, target, level, req);
    }

    @Test
    public void testPlayerMissionsByPut() {

        PlayerMissions playerMissions = new PlayerMissions();
        Objective ob1 = getObjective(MISSION_1, "c",null);

        playerMissions.putObjective(OB1, ob1, 2);

        assertEquals(2, playerMissions.getProgress(OB1), 0.0);


    }


    @Test
    public void testPlayerMissionsByMaps() {

        PlayerMissions playerMissions = new PlayerMissions();
        Objective ob1 = getObjective(MISSION_1, "c",null);

        playerMissions.putObjective(OB1, ob1, 2);

        assertEquals(1, playerMissions.getProgress().size());
        assertEquals(1, playerMissions.getObjectives().size());


    }


    @Test
    public void testObjective() {

        Objective ob = getObjective(MISSION_1, "c", null);

        assertTrue(ob.match("c", null));
    }
    @Test
    public void testPlayerMissionsByAddProgress() {

        PlayerMissions playerMissions = new PlayerMissions();

        Objective ob1 = getObjective(MISSION_1, "c",null);
        Objective ob2 = getObjective(MISSION_1, "c", 3);
        Objective ob3 = getObjective(MISSION_2, "p3", null);

        playerMissions.putObjective(OB1, ob1, 0);
        playerMissions.putObjective(OB2, ob2, 0);
        playerMissions.putObjective(OB3, ob3, 0);

        playerMissions.addProgress("c", null);

        assertEquals(1, playerMissions.getProgress(OB1).intValue());
        assertEquals(0, playerMissions.getProgress(OB2).intValue());
        assertEquals(0, playerMissions.getProgress(OB3).intValue());
    }

    @Test
    public void testPlayerMissionsByCompleteProgress() {

        PlayerMissions playerMissions = new PlayerMissions();

        Objective ob1 = getObjective(MISSION_1, "c",null, 6);
        Objective ob2 = getObjective(MISSION_1, "c", 3, 6);
        Objective ob3 = getObjective(MISSION_2, "p3", null, 6);

        playerMissions.putObjective(OB1, ob1, 0);
        playerMissions.putObjective(OB2, ob2, 0);
        playerMissions.putObjective(OB3, ob3, 0);

        for(int i = 0; i < 5; i++) {
            playerMissions.addProgress("c", 5);
        }

        Map<Long, Grade> result = playerMissions.addProgress("c", 5);

        assertTrue(result.containsKey(MISSION_1));
        assertEquals(Grade.A, result.get(MISSION_1));
        assertEquals(1, result.size());

        assertFalse(ob1.isObserved());
        assertFalse(ob2.isObserved());
        assertEquals(1, playerMissions.getProgress().size());
        assertEquals(1, playerMissions.getObjectives().size());
    }

    @Test
    public void testPlayerMissionsByMultipleCompleteProgress() {

        PlayerMissions playerMissions = new PlayerMissions();

        Objective ob1 = getObjective(MISSION_1, "c",null, 6);
        Objective ob2 = getObjective(MISSION_2, "c", 3, 6);

        playerMissions.putObjective(OB1, ob1, 0);
        playerMissions.putObjective(OB2, ob2, 0);

        for(int i = 0; i < 5; i++) {
            playerMissions.addProgress("c", 5);
        }

        Map<Long, Grade> result = playerMissions.addProgress("c", 5);

        assertTrue(result.containsKey(MISSION_1));
        assertEquals(Grade.A, result.get(MISSION_1));
        assertTrue(result.containsKey(MISSION_2));
        assertEquals(Grade.A, result.get(MISSION_2));
        assertEquals(2, result.size());

        assertFalse(ob1.isObserved());
        assertFalse(ob2.isObserved());
        assertEquals(0, playerMissions.getProgress().size());
        assertEquals(0, playerMissions.getObjectives().size());
    }


}
