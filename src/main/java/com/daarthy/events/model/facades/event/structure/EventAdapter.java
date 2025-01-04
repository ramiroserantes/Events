package com.daarthy.events.model.facades.event.structure;

import com.daarthy.events.model.facades.event.structure.plans.Plan;
import com.daarthy.events.model.facades.event.structure.plans.PlanFactoryImpl;
import com.daarthy.events.persistence.daos.event.entities.ContributionKey;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.util.HashMap;
import java.util.Map;

public class EventAdapter implements ExtendedEvent {

    private final EventData eventData;
    private final Plan plan;
    private final Map<EventsPlayer, PlayerContribution> players = new HashMap<>();

    public EventAdapter(EventData eventData) {
        this.eventData = eventData;
        this.plan = new PlanFactoryImpl().createEventPlan(eventData.getName());
    }

    @Override
    public void addPlayer(EventsPlayer player, PlayerContribution contribution) {
        players.put(player, contribution);
    }

    @Override
    public PlayerContribution applyPlan(EventsPlayer player, ActionType actionType) {

        if (plan.matchActivity(actionType)) {
            PlayerContribution contribution = players.computeIfAbsent(player, contributionPlayer ->
                    PlayerContribution.builder()
                            .contributionKey(ContributionKey.builder()
                                    .eventId(eventData.getId())
                                    .playerId(player.getPlayerId())
                                    .build())
                            .medals(0)
                            .items(0)
                            .build());

            if (plan.applyPlan(contribution.getItems())) {
                contribution.increaseItems();
                contribution.increaseMedals(eventData.getMaxMedals());
                return contribution;
            }
        }
        return null;
    }

    @Override
    public EventData getEventData() {
        return eventData;
    }

    @Override
    public Map<EventsPlayer, PlayerContribution> getPlayers() {
        return players;
    }

    @Override
    public Plan getPlan() {
        return plan;
    }
}
