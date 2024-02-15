package com.daarthy.events.app.modules.missions;

import com.daarthy.events.persistence.missionDao.MissionData;
import com.daarthy.events.persistence.missionDao.ObjectiveData;

import java.util.HashMap;
import java.util.List;

public interface MissionFactory {

    HashMap<MissionData, List<ObjectiveData>> getMission(Grade grade, boolean isDefaultGuild);
}
