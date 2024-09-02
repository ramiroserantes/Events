package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptKey;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class MissionAcceptanceJdbc extends AbstractMiniSR<MissionAcceptance, MissionAcceptKey>
        implements MissionAcceptanceDao{

    public MissionAcceptanceJdbc(HikariDataSource dataSource) {
        super(dataSource, MissionAcceptance.class);
    }
}
