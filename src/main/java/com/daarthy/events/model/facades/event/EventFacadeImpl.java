package com.daarthy.events.model.facades.event;

import com.daarthy.events.model.facades.event.components.*;
import com.daarthy.events.model.facades.event.memory.EventMemory;
import com.daarthy.events.model.facades.event.memory.EventMemoryImpl;
import com.daarthy.events.persistence.PersistenceContext;

import java.util.concurrent.TimeUnit;

public class EventFacadeImpl implements EventFacade {

    private static final Long MEMORY_LOOP = 8L;
    private final EventMemory eventMemory;

    // Components
    private final EventLgnComp eventLgnComp;
    private final EventActionsComp eventActionsComp;
    private final EventInfoComp eventInfoComp;

    public EventFacadeImpl(PersistenceContext persistenceContext) {
        this.eventMemory = EventMemoryImpl.getInstance(MEMORY_LOOP, TimeUnit.MINUTES, persistenceContext);

        this.eventLgnComp = new EventLgnCompImpl(eventMemory, persistenceContext);
        this.eventActionsComp = new EventActionsCompImpl(eventMemory, persistenceContext);
        this.eventInfoComp = new EventInfoCompImpl(eventMemory, persistenceContext);
    }

    @Override
    public EventLgnComp lgnComponent() {
        return eventLgnComp;
    }

    @Override
    public EventActionsComp actionsComponent() {
        return eventActionsComp;
    }

    @Override
    public EventInfoComp infoComponent() {
        return eventInfoComp;
    }

    @Override
    public EventMemory eventMemory() {
        return eventMemory;
    }
}
