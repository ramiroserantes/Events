package com.daarthy.events.persistence.factories.missions;

import com.daarthy.events.persistence.daos.mission.Grade;
import com.daarthy.events.persistence.daos.mission.MissionData;
import com.daarthy.events.persistence.daos.mission.ObjectiveData;

import java.util.HashMap;
import java.util.List;

public interface MissionFactory {

    HashMap<MissionData, List<ObjectiveData>> getMission(Grade grade, boolean isDefaultGuild);
}
