package com.daarthy.events.app.modules.missions;

import com.daarthy.events.persistence.factories.missions.MissionFactory;
import com.daarthy.events.persistence.factories.missions.MissionFactoryImpl;
import com.daarthy.events.persistence.daos.mission.Grade;
import com.daarthy.events.persistence.daos.mission.MissionData;
import com.daarthy.events.persistence.daos.mission.ObjectiveData;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MissionFactoryTest {

    @Test
    public void testMissionFactoryByFGradeAndNoDefaultGuild() {

        MissionFactory missionFactory = new MissionFactoryImpl();

        HashMap<MissionData, List<ObjectiveData>> mission = missionFactory.getMission(Grade.F, false);

        assertFalse(mission.isEmpty());
    }
}
