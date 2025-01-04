package com.daarthy.events.model.facades.data;

import com.daarthy.events.model.facades.data.components.DataCreationComp;
import com.daarthy.events.model.facades.data.components.DataFinderComp;
import com.daarthy.events.model.facades.data.components.DataRemovalComp;
import com.daarthy.events.model.facades.data.memory.DataMemory;

public interface DataFacade {

    /**
     * Provides access to the component responsible for retrieving and
     * caching data entities such as players and guilds.
     *
     * @return the {@link DataFinderComp} instance.
     */
    DataFinderComp finderComponent();

    /**
     * Provides access to the component responsible for creating new
     * data entities and managing their persistence and caching.
     *
     * @return the {@link DataCreationComp} instance.
     */
    DataCreationComp creationComponent();

    /**
     * Provides access to the component responsible for removing or
     * logging out data entities, ensuring proper cleanup of cache
     * and persistence layers.
     *
     * @return the {@link DataRemovalComp} instance.
     */
    DataRemovalComp removalComponent();

    DataMemory dataMemory();
}
