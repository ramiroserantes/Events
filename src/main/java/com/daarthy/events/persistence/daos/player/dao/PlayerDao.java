package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.hibernate.jdbc.MiniSRE;

import java.util.UUID;

public interface PlayerDao extends MiniSRE<EventsPlayer, UUID> {

}
