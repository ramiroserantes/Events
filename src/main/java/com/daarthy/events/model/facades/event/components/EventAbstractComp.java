package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.model.facades.event.memory.EventMemory;
import com.daarthy.events.persistence.PersistenceContext;

abstract class EventAbstractComp {
    protected final EventMemory memory;
    protected final PersistenceContext persistenceContext;

    protected EventAbstractComp(EventMemory memory, PersistenceContext persistenceContext) {
        this.memory = memory;
        this.persistenceContext = persistenceContext;
    }

}
