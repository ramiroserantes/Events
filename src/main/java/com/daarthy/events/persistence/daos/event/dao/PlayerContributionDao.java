package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.daos.event.entities.ContributionKey;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.mini.hibernate.jdbc.MiniSR;

public interface PlayerContributionDao extends MiniSR<PlayerContribution, ContributionKey> {
}
