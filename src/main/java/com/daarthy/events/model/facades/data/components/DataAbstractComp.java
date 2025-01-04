package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.persistence.PersistenceContext;

abstract class DataAbstractComp {
    protected final DataMemory memory;
    protected final PersistenceContext persistenceContext;

    protected DataAbstractComp(DataMemory memory, PersistenceContext persistenceContext) {
        this.memory = memory;
        this.persistenceContext = persistenceContext;
    }
}
