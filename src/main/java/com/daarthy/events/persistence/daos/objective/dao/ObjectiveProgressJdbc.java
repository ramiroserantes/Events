package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class ObjectiveProgressJdbc extends AbstractMiniSR<ObjectiveProgress, ObjectiveProgressKey>
        implements ObjectiveProgressDao {

    public ObjectiveProgressJdbc(HikariDataSource dataSource) {
        super(dataSource, ObjectiveProgress.class);
    }
}
