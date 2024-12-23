package com.daarthy.events.model.modules.events;

public class EventImpl implements Event {

    /*private EventData eventData;
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
                contribution.increaseMedals(eventData.getMaxMedals());
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
        Contribution contribution = players.getOrDefault(playerId, null);
        if(contribution != null) {
            players.remove(playerId);
            return contribution;
        }
        return null;
    }

    @Override
    public Map<UUID, Contribution> getPlayers() {
        return players;
    }

    @Override
    public EventData getData() {
        return eventData;
    }*/
}
