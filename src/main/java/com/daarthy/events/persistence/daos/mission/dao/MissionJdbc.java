package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSRECriteria;
import com.zaxxer.hikari.HikariDataSource;

public class MissionJdbc extends AbstractMiniSRECriteria<Mission, Long> implements MissionDao {

    public MissionJdbc(HikariDataSource dataSource) {
        super(dataSource, Mission.class);
    }
}
