package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSRECriteria;
import com.zaxxer.hikari.HikariDataSource;

public class ObjectiveProgressJdbc extends AbstractMiniSRECriteria<ObjectiveProgress, ObjectiveProgressKey>
        implements ObjectiveProgressDao {

    public ObjectiveProgressJdbc(HikariDataSource dataSource) {
        super(dataSource, ObjectiveProgress.class);
    }
}
