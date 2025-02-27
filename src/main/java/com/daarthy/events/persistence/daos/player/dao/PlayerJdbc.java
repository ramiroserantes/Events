package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.hibernate.jdbc.AbstractMiniSR;
import com.zaxxer.hikari.HikariDataSource;

import java.util.UUID;

public class PlayerJdbc extends AbstractMiniSR<EventsPlayer, UUID> implements PlayerDao {

    public PlayerJdbc(HikariDataSource dataSource) {
        super(dataSource, EventsPlayer.class);
    }

}
