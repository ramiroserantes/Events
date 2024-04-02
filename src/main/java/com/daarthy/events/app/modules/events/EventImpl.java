package com.daarthy.events.app.modules.events;

import com.daarthy.events.app.modules.events.plans.Plan;
import com.daarthy.events.app.modules.events.plans.PlanFactoryImpl;
import com.daarthy.events.persistence.event_dao.Contribution;
import com.daarthy.events.persistence.event_dao.EventData;
import com.daarthy.events.persistence.mission_dao.ActionType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventImpl implements Event {

    private EventData eventData;
    private Plan plan;
    private Map<UUID, Contribution> players = new HashMap<>();

    public EventImpl(EventData eventData) {
        this.eventData = eventData;
        this.plan = new PlanFactoryImpl().createEventPlan(eventData.getName());
    }


    @Override
    public void addPlayer(UUID playerId, Contribution contribution) {
        players.put(playerId, contribution);
    }

    @Override
    public Contribution applyPlan(UUID playerId, ActionType actionType) {

        if(plan.matchActivity(actionType)) {
            Contribution contribution = players.get(playerId);
            if(plan.applyPlan(contribution.getItems())) {
                contribution.increaseItems();
                contribution.increaseMedals();
                return contribution;
            }
        }
        return null;
    }

    @Override
    public Plan getPlan() {
        return plan;
    }

    @Override
    public Contribution removePlayer(UUID playerId) {
        Contribution contribution = players.get(playerId);
        players.remove(playerId);
        return contribution;
    }

    @Override
    public Map<UUID, Contribution> getPlayers() {
        return players;
    }

    @Override
    public EventData getData() {
        return eventData;
    }
}
