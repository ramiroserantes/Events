package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import com.daarthy.mini.hibernate.jdbc.MiniSRECriteria;

public interface ObjectiveProgressDao extends MiniSRECriteria<ObjectiveProgress, ObjectiveProgressKey> {
}
