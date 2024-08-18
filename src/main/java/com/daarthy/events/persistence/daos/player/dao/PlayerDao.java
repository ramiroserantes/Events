package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.hibernate.jdbc.MiniCrud;

import java.util.UUID;

public interface PlayerDao extends MiniCrud<EventsPlayer, UUID> {

    void removeAllPlayersFromGuild(Long guildId);

}
