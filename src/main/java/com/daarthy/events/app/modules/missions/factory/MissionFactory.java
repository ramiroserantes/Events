package com.daarthy.events.app.modules.missions.factory;

import com.daarthy.events.persistence.mission_dao.Grade;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.mission_dao.ObjectiveData;

import java.util.HashMap;
import java.util.List;

public interface MissionFactory {

    HashMap<MissionData, List<ObjectiveData>> getMission(Grade grade, boolean isDefaultGuild);
}
