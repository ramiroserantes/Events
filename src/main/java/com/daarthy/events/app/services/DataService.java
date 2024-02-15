package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.GuildCache;
import com.daarthy.events.persistence.guildDao.GuildData;
import com.daarthy.events.persistence.playerDao.PlayerData;

import java.util.UUID;

public interface DataService {

    PlayerData getPlayerData(UUID playerId);

    Long createGuild(Long guildId, String kName);

    GuildCache getGuildByPlayer(UUID playerId, PlayerData playerData);

    void savePlayer(UUID playerId);

    void saveGuild(Long guildId);

    void removePlayerFromCache(UUID playerId);

    void removeGuildFromCache(Long guildId);

    void deleteGuild(Long guildId);

}
