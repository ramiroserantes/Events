package com.daarthy.events.persistence.daos.guild.dao;

import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSREDCriteria;
import com.zaxxer.hikari.HikariDataSource;

public class GuildJdbc extends AbstractMiniSREDCriteria<Guild, Long>
        implements GuildDao {

    public GuildJdbc(HikariDataSource dataSource) {
        super(dataSource, Guild.class);
    }

}
