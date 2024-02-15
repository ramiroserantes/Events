package com.daarthy.events.app.modules.missions;

import com.daarthy.events.persistence.missionDao.MissionData;
import com.daarthy.events.persistence.missionDao.ObjectiveData;
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
