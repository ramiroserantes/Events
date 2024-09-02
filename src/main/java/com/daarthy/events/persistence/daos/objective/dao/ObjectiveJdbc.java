package com.daarthy.events.persistence.daos.objective.dao;

import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class ObjectiveJdbc extends AbstractMiniSR<Objective, Long> implements ObjectiveDao {

    public ObjectiveJdbc(HikariDataSource dataSource) {
        super(dataSource, Objective.class);
    }
}
