package com.daarthy.events.app.modules.events;

import com.daarthy.events.app.modules.events.plans.Plan;
import com.daarthy.events.persistence.eventDao.Contribution;
import com.daarthy.events.persistence.missionDao.ActionType;

import java.util.Map;
import java.util.UUID;

public interface Event {

    void addPlayer(UUID playerId, Contribution contribution);

    Contribution applyPlan(UUID playerId, ActionType actionType);

    Plan getPlan();

    Contribution removePlayer(UUID playerId);

    Map<UUID, Contribution> getPlayers();
}
