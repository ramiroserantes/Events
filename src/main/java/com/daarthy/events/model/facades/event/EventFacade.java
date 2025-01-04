package com.daarthy.events.model.facades.event;

import com.daarthy.events.model.facades.event.components.EventActionsComp;
import com.daarthy.events.model.facades.event.components.EventInfoComp;
import com.daarthy.events.model.facades.event.components.EventLgnComp;
import com.daarthy.events.model.facades.event.memory.EventMemory;

public interface EventFacade {

    /**
     * Provides access to the component responsible for player login and logout actions.
     *
     * @return the {@link EventLgnComp} instance handling login and logout operations.
     */
    EventLgnComp lgnComponent();

    /**
     * Provides access to the component responsible for managing event-related actions.
     *
     * @return the {@link EventActionsComp} instance handling event actions.
     */
    EventActionsComp actionsComponent();

    /**
     * Provides access to the component responsible for retrieving event information.
     *
     * @return the {@link EventInfoComp} instance handling event information retrieval.
     */
    EventInfoComp infoComponent();

    /**
     * Exposes the in-memory structure used for maintaining the state of active events.
     *
     * @return the {@link EventMemory} instance managing event data in memory.
     */
    EventMemory eventMemory();
}
