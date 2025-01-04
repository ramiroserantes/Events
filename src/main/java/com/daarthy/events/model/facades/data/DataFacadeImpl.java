package com.daarthy.events.model.facades.data;

import com.daarthy.events.model.facades.data.components.*;
import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.model.facades.data.memory.DataMemoryImpl;
import com.daarthy.events.persistence.PersistenceContext;

import java.util.concurrent.TimeUnit;

public class DataFacadeImpl implements DataFacade {

    private static final Long MEMORY_LOOP = 4L;
    private final DataMemory dataMemory;

    // Components
    private final DataFinderComp dataFinderComp;
    private final DataCreationComp dataCreationComp;
    private final DataRemovalComp dataRemovalComp;

    public DataFacadeImpl(PersistenceContext persistenceContext) {
        this.dataMemory = DataMemoryImpl.getInstance(MEMORY_LOOP, TimeUnit.MINUTES, persistenceContext);

        this.dataFinderComp = new DataFinderCompImpl(dataMemory, persistenceContext);
        this.dataCreationComp = new DataCreationCompImpl(dataMemory, persistenceContext, this.dataFinderComp);
        this.dataRemovalComp = new DataRemovalCompImpl(dataMemory, persistenceContext, this.dataFinderComp);
    }


    @Override
    public DataFinderComp finderComponent() {
        return dataFinderComp;
    }

    @Override
    public DataCreationComp creationComponent() {
        return dataCreationComp;
    }

    @Override
    public DataRemovalComp removalComponent() {
        return dataRemovalComp;
    }

    @Override
    public DataMemory dataMemory() {
        return dataMemory;
    }
}
