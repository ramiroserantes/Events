package com.daarthy.events.app.modules.missions;

import com.daarthy.events.app.modules.missions.factory.MissionFactory;
import com.daarthy.events.app.modules.missions.factory.MissionFactoryImpl;
import com.daarthy.events.persistence.mission_dao.Grade;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.mission_dao.ObjectiveData;
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
