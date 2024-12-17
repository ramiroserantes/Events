package com.daarthy.events.persistence.daos;

import com.daarthy.mini.hibernate.jdbc.AbstractMiniGenerics;
import com.zaxxer.hikari.HikariDataSource;

public class SearchJdbc extends AbstractMiniGenerics implements SearchDao {

    public SearchJdbc(HikariDataSource dataSource) {
        super(dataSource);
    }
}
