package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.daos.event.entities.GuildMedals;
import com.daarthy.events.persistence.daos.event.entities.GuildMedalsKey;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class GuildMedalsDaoJdbc extends AbstractMiniSR<GuildMedals, GuildMedalsKey>
        implements GuildMedalsDao {

    public GuildMedalsDaoJdbc(HikariDataSource datasource) {
        super(datasource, GuildMedals.class);
    }
}
