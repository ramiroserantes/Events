package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.daos.event.entities.ContributionKey;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

public class PlayerContributionJdbc extends AbstractMiniSR<PlayerContribution, ContributionKey>
        implements PlayerContributionDao {

    public PlayerContributionJdbc(HikariDataSource datasource) {
        super(datasource, PlayerContribution.class);
    }
}
