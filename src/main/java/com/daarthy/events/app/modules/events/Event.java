package com.daarthy.events.app.modules.events;

import com.daarthy.events.app.modules.events.plans.Plan;
import com.daarthy.events.persistence.event_dao.Contribution;
import com.daarthy.events.persistence.event_dao.EventData;
import com.daarthy.events.persistence.mission_dao.ActionType;

import java.util.Map;
import java.util.UUID;

public interface Event {

    void addPlayer(UUID playerId, Contribution contribution);

    Contribution removePlayer(UUID playerId);

    Contribution applyPlan(UUID playerId, ActionType actionType);

    Map<UUID, Contribution> getPlayers();

    EventData getData();

    Plan getPlan();
}
