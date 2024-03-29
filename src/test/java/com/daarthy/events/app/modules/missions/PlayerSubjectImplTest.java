package com.daarthy.events.app.modules.missions;


import com.daarthy.events.app.modules.missions.observers.Objective;
import com.daarthy.events.app.modules.missions.observers.ObservableObjective;
import com.daarthy.events.app.modules.missions.observers.PlayerSubject;
import com.daarthy.events.app.modules.missions.observers.PlayerSubjectImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerSubjectImplTest {

    private static final Long missionId1 = 1L;
    private static final Long missionId2 = 2L;

    private static final Long objectiveId1 = 1L;
    private static final Long objectiveId2 = 2L;
    private static final Long objectiveId3 = 3L;
    private static final Long objectiveId4 = 4L;

    @Test
    public void testObserverByEmptyCreation() {


        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        assertNull(playerSubject.getCurrentProgress(objective));
    }

    @Test
    public void testObserverByCreation() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);
        objectives.add(objective);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        assertEquals(playerSubject.getCurrentProgress(objective), 0, 0.0);
    }

    @Test
    public void testObserverByAddProgressByEqualObjective() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);
        objectives.add(objective);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        playerSubject.addProgress("ZOMBIE", 12);

        assertEquals(playerSubject.getCurrentProgress(objective), 1, 0.0);
    }

    @Test
    public void testObserverByAddProgressByNoEqualObjective() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);
        objectives.add(objective);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        playerSubject.addProgress("ZOM", 12);

        assertEquals(playerSubject.getCurrentProgress(objective), 0, 0.0);
    }

    @Test
    public void testObserverByAddProgressByInferiorLevel() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 90);
        objectives.add(objective);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        playerSubject.addProgress("ZOMBIE", 10);

        assertEquals(playerSubject.getCurrentProgress(objective), 0, 0.0);
    }

    @Test
    public void testObserverByAddProgressBySimpleCompletedObjective() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 1);
        objectives.add(objective);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        List<ObservableObjective> completed = playerSubject.addProgress("ZOMBIE", 12);

        assertEquals(completed.size(), 1);
    }

    @Test
    public void testObserverByAddProgressByMultipleObjective() {

        List<ObservableObjective> objectives = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 1);
        ObservableObjective objective2 = new Objective(objectiveId1, "ZOMBIE", 12, 10);
        objectives.add(objective);
        objectives.add(objective2);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        List<ObservableObjective> completed = playerSubject.addProgress("ZOMBIE", 12);

        assertEquals(completed.size(), 1);
    }

    @Test
    public void testObserverByAddProgressByMultipleObjectiveInMissions() {

        List<ObservableObjective> objectives1 = new ArrayList<>();
        List<ObservableObjective> objectives2 = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 1);
        ObservableObjective objective2 = new Objective(objectiveId1, "ZOMBIE", 12, 2);
        objectives1.add(objective);
        objectives2.add(objective2);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives1);
        mission.put(missionId2, objectives2);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);
        progress.put(objective2, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        List<ObservableObjective> completed1 = playerSubject.addProgress("ZOMBIE", 12);
        List<ObservableObjective> completed2 = playerSubject.addProgress("ZOMBIE", 12);

        assertEquals(completed1.size(), 1);
        assertEquals(completed2.size(), 1);
    }

    @Test
    public void testObserverByAddProgressByMissionReady() {

        List<ObservableObjective> objectives1 = new ArrayList<>();
        List<ObservableObjective> objectives2 = new ArrayList<>();
        ObservableObjective objective = new Objective(objectiveId1, "ZOMBIE", 12, 1);
        ObservableObjective objective2 = new Objective(objectiveId1, "ZOMBIE", 12, 2);
        objectives1.add(objective);
        objectives2.add(objective2);

        HashMap<Long, List<ObservableObjective>> mission = new HashMap<>();
        mission.put(missionId1, objectives1);
        mission.put(missionId2, objectives2);
        HashMap<ObservableObjective, Integer> progress = new HashMap<>();
        progress.put(objective, 0);
        progress.put(objective2, 0);

        PlayerSubject playerSubject = new PlayerSubjectImpl(mission, progress);

        playerSubject.addProgress("ZOMBIE", 12);

        assertTrue(playerSubject.isMissionCompleted(missionId1));
        assertFalse(playerSubject.isMissionCompleted(missionId2));
    }


}
