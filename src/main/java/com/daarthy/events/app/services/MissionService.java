package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.GuildCache;
import com.daarthy.events.persistence.missionDao.Grade;
import com.daarthy.events.persistence.missionDao.CompletionData;
import com.daarthy.events.persistence.missionDao.FailedMission;
import com.daarthy.events.persistence.missionDao.MissionData;
import com.daarthy.events.persistence.missionDao.ObjectiveData;
import com.daarthy.events.persistence.playerDao.PlayerData;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface MissionService {

    StringBuilder joinMission(UUID playerId, PlayerData playerData, GuildCache guildCache, MissionData missionData);

    void fillGuildDashBoard(Long guildId, GuildCache guildCache);

    List<FailedMission> updateFailedMissions(Long guildId);

    List<Grade> addProgress(UUID playerId, String target, Integer level);

    HashMap<MissionData, List<ObjectiveData>> findGuildDashBoard(Long guildId);

    HashMap<MissionData, List<ObjectiveData>> findPlayerDashBoard(UUID playerId);

    HashMap<String, CompletionData> getPlayerRates(UUID playerId);

    void savePlayer(UUID playerId);

    void initPlayer(UUID playerId);

    void removePlayer(UUID playerId);

}
