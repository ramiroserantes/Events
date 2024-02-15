package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.GuildCache;
import com.daarthy.events.persistence.missionDao.MissionData;
import com.daarthy.events.persistence.playerDao.PlayerData;

import java.util.UUID;

public interface MissionService {

    StringBuilder joinMission(UUID playerId, PlayerData playerData, GuildCache guildCache, MissionData missionData);

    void savePlayer(UUID playerId);

    void initPlayer(UUID playerId);

    void removePlayer(UUID playerId);

    void fillGuildDashBoard(GuildCache guildCache);
    /*
    * GetPlayerDashBoard.
    * GetGuildDashBoard.
    * FillGuildDashBoard.
    * */
}
