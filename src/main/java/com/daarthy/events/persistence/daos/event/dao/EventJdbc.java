package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class EventJdbc extends AbstractMiniSR<EventData, Long> implements EventDao {

    public EventJdbc(HikariDataSource datasource) {
        super(datasource, EventData.class);
    }

}
