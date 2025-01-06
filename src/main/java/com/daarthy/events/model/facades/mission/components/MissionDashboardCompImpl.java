package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.model.facades.mission.memory.MissionMemory;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MissionDashboardCompImpl extends MissionAbstractComp implements MissionDashboardComp {

    public MissionDashboardCompImpl(MissionMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public Map<Mission, List<Objective>> findPlayerDashBoard(UUID playerId) {
        return Map.of();
    }

    @Override
    public Map<Mission, List<Objective>> findGuildDashBoard(Long guildId) {
        return Map.of();
    }

    @Override
    public void fillGuildDashBoard(Long guildId, ExtendedGuild guild) {
        
    }
}
