package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.model.facades.event.memory.EventMemory;
import com.daarthy.events.model.facades.event.structure.ExtendedEvent;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.event.entities.GuildMedalsKey;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.tokens.EventToken;

import java.util.ArrayList;
import java.util.List;

public class EventActionsCompImpl extends EventAbstractComp implements EventActionsComp {

    public EventActionsCompImpl(EventMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public List<EventToken> registerAction(EventsPlayer player, ActionType actionType) {
        List<EventToken> eventTokens = new ArrayList<>();

        for (ExtendedEvent extendedEvent : memory.getEvents()) {
            PlayerContribution contribution = extendedEvent.applyPlan(player, actionType);
            if (contribution != null) {
                eventTokens.add(EventToken.fromString(extendedEvent.getEventData().getName()));
                if (extendedEvent.getEventData().getMaxMedals() >= contribution.getMedals()) {
                    memory.getGuildMedals().get(GuildMedalsKey.builder()
                                    .eventId(extendedEvent.getEventData().getId())
                                    .guildId(player.getGuildId())
                                    .build())
                            .addMedals();
                }
            }
        }

        return eventTokens;
    }
}
