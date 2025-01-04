package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.model.exceptions.ExceptionKey;
import com.daarthy.events.model.facades.event.memory.EventMemory;
import com.daarthy.events.model.facades.event.structure.ExtendedEvent;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;

import java.text.DecimalFormat;
import java.util.Set;
import java.util.stream.Collectors;

public class EventInfoCompImpl extends EventAbstractComp implements EventInfoComp {

    public EventInfoCompImpl(EventMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public Set<EventData> getActiveEvents() {
        return memory.getEvents().stream()
                .map(ExtendedEvent::getEventData)
                .collect(Collectors.toSet());
    }

    @Override
    public StringBuilder getEventInfo(EventsPlayer player, MiniAuth miniAuth, String eventName) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        StringBuilder result = new StringBuilder();
        for (ExtendedEvent e : memory.getEvents()) {
            if (e.getEventData().getName().equalsIgnoreCase(eventName)) {
                PlayerContribution contribution = e.getPlayers().get(player);
                String i18Message = persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                        e.getEventData().getName());
                result.append(String.format(i18Message,
                        decimalFormat.format(e.getPlan().getCoefficient()),
                        decimalFormat.format(contribution.getItems()),
                        decimalFormat.format(e.getPlan().getProbability(contribution.getItems())),
                        decimalFormat.format(e.getPlan().getLimit())));
            }
        }

        if (result.toString().isEmpty()) {
            result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                    ExceptionKey.EVENT_NOT_AVAILABLE.name()));
        }

        return result;
    }
}
