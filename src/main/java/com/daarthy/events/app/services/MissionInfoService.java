package com.daarthy.events.app.services;

import com.daarthy.events.persistence.mission_dao.CompletionData;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.mission_dao.ObjectiveData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MissionInfoService {

    Map<MissionData, List<ObjectiveData>> findGuildDashBoard(Long guildId);

    Map<String, CompletionData> getPlayerRates(UUID playerId);

}
