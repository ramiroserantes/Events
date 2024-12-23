package com.daarthy.events.persistence.factories.missions;

import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MissionFactoryTest {

    private final MissionFactory missionFactory = new MissionFactoryImpl();

    @Test
    public void testBySimpleMissionDefaultGuildWithoutRestrictions() {

        Set<String> usedMissions = new HashSet<>();
        Map<Mission, List<Objective>> missionObjectiveMap = missionFactory.getMission(Grade.D, true,
                usedMissions);

        assertEquals(1, usedMissions.size());
        assertEquals(1, missionObjectiveMap.size());

        Mission mission = missionObjectiveMap.keySet().iterator().next();
        List<Objective> objectives = missionObjectiveMap.get(mission);

        assertNull(mission.getCurrentPlayers());
        assertNull(mission.getMaxCompletions());
        assertNotNull(mission.getTitle());

        assertNotNull(objectives.get(0).getActionType());
        assertNotNull(objectives.get(0).getRequiredAmount());
    }

    @Test
    public void testBySimpleMissionsGuildWithRestrictions() {

        Set<String> usedMissions = new HashSet<>();

        Map<Mission, List<Objective>> missionObjectiveMap = missionFactory.getMission(Grade.D, false,
                usedMissions);

        assertEquals(1, usedMissions.size());
        assertEquals(1, missionObjectiveMap.size());

        Mission mission = missionObjectiveMap.keySet().iterator().next();
        List<Objective> objectives = missionObjectiveMap.get(mission);

        assertNull(mission.getCurrentPlayers());
        assertNotNull(mission.getMaxCompletions());
        assertNotNull(mission.getTitle());

        assertNotNull(objectives.get(0).getActionType());
        assertNotNull(objectives.get(0).getRequiredAmount());
    }
}
