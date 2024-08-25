package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSRE;
import com.zaxxer.hikari.HikariDataSource;

import java.util.UUID;

public class PlayerJdbc extends AbstractMiniSRE<EventsPlayer, UUID> implements PlayerDao {
    public PlayerJdbc(HikariDataSource dataSource) {
        super(dataSource, EventsPlayer.class);
    }

}
