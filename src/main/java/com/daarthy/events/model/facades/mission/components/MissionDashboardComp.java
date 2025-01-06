package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MissionDashboardComp {

    Map<Mission, List<Objective>> findPlayerDashBoard(UUID playerId);

    Map<Mission, List<Objective>> findGuildDashBoard(Long guildId);

    void fillGuildDashBoard(Long guildId, ExtendedGuild guild);
}
