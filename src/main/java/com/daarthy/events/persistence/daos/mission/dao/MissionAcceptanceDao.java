package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptKey;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.mini.hibernate.jdbc.MiniSRECriteria;

public interface MissionAcceptanceDao extends MiniSRECriteria<MissionAcceptance, MissionAcceptKey> {
}
