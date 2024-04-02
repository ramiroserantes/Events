package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.player_dao.PlayerData;

import java.util.UUID;

public interface DataService {

    PlayerData getPlayerData(UUID playerId);

    Guild getGuild(Long guildId);

    Guild findDBGuild(Long guildId);

    void createGuild(UUID playerId, Long guildId, String kName);

    void deleteGuild(Long guildId);

    /**
     *
     * Cache Ops
     *
     * **/

    void initPlayer(UUID playerId);

    void savePlayer(UUID playerId);

    void saveGuild(Long guildId);

    void removePlayer(UUID playerId);

    void removeGuild(Long guildId);



}
