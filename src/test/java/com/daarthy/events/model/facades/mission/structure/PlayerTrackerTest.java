package com.daarthy.events.model.facades.mission.structure;

import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class PlayerTrackerTest {

    private PlayerTrackerImpl playerTracker;
    private ExtendedObjective mockObjective;

    @Before
    public void setUp() {
        playerTracker = new PlayerTrackerImpl();

        mockObjective = new ExtendedObjective(Objective.builder()
                .id(1L)
                .missionId(1L)
                .requiredAmount(2)
                .target("kill_monster")
                .levels(5)
                .build(), LocalDate.now());

        ObjectiveProgress mockProgress = ObjectiveProgress.builder()
                .key(ObjectiveProgressKey.builder()
                        .playerId(UUID.randomUUID())
                        .objectiveId(1L)
                        .build())
                .amount(0)
                .build();

        playerTracker.getObjectiveTrack().put(mockObjective, mockProgress);
    }

    @Test
    public void testTrackActionProgressesObjective() {
        Map<ExtendedObjective, ObjectiveProgress> completed = playerTracker.trackAction("kill_monster", 1);

        assertTrue(playerTracker.getObjectiveTrack().containsKey(mockObjective));
        assertEquals(0, (int) playerTracker.getObjectiveTrack().get(mockObjective).getAmount());

        assertTrue(completed.isEmpty());
    }

    @Test
    public void testTrackActionCompletesObjective() {
        playerTracker.trackAction("kill_monster", 5);
        Map<ExtendedObjective, ObjectiveProgress> completed = playerTracker.trackAction("kill_monster", 6);

        assertFalse(playerTracker.getObjectiveTrack().containsKey(mockObjective));
        assertTrue(completed.containsKey(mockObjective));
    }

    @Test
    public void testIsMissionCompletedReturnsTrueForExistingMission() {
        assertFalse(playerTracker.isMissionCompleted(1L));
        playerTracker.trackAction("kill_monster", 5);
        playerTracker.trackAction("kill_monster", 5);

        assertTrue(playerTracker.isMissionCompleted(1L));
    }

    @Test
    public void testIsMissionCompletedReturnsFalseForNonExistingMission() {
        // If the PlayerTracker doesn't have any objective with the missionId, then its completed.
        assertTrue(playerTracker.isMissionCompleted(999L));
    }
}
