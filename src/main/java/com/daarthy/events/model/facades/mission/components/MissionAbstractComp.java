package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.model.facades.mission.memory.MissionMemory;
import com.daarthy.events.persistence.PersistenceContext;

abstract class MissionAbstractComp {
    protected final MissionMemory memory;
    protected final PersistenceContext persistenceContext;

    protected MissionAbstractComp(MissionMemory memory, PersistenceContext persistenceContext) {
        this.memory = memory;
        this.persistenceContext = persistenceContext;
    }
}
