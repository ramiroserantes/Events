package com.daarthy.events.persistence.daos;

import com.daarthy.mini.hibernate.jdbc.AbstractMiniGenerics;
import com.zaxxer.hikari.HikariDataSource;

public class SearchDaoJdbc extends AbstractMiniGenerics implements SearchDao {

    public SearchDaoJdbc(HikariDataSource dataSource) {
        super(dataSource);
    }
}
