package com.daarthy.events.app.services;

import com.daarthy.events.persistence.daos.mission.CompletionData;
import com.daarthy.events.persistence.daos.mission.MissionData;
import com.daarthy.events.persistence.daos.mission.ObjectiveData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MissionInfoService {

    Map<MissionData, List<ObjectiveData>> findGuildDashBoard(Long guildId);

    Map<String, CompletionData> getPlayerRates(UUID playerId);

}
