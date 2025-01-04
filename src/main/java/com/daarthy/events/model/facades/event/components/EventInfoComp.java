package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;

import java.util.Set;

public interface EventInfoComp {

    /**
     * Retrieves the set of all active events currently in memory.
     *
     * @return a {@link Set} of {@link EventData} objects representing active events.
     */
    Set<EventData> getActiveEvents();

    /**
     * Retrieves detailed information about a specific event for a given player.
     *
     * <p>The event information includes details like the event's coefficient,
     * the player's contribution (e.g., items collected), probabilities, and any
     * applicable limits. If the event is not available, a localized message is
     * returned to indicate its unavailability.</p>
     *
     * @param player    the {@link EventsPlayer} requesting the event information.
     * @param miniAuth  the {@link MiniAuth} object for authentication and localization.
     * @param eventName the name of the event for which information is requested.
     * @return a {@link StringBuilder} containing the localized event details, or
     * a message indicating that the event is not available.
     */
    StringBuilder getEventInfo(EventsPlayer player, MiniAuth miniAuth, String eventName);
}
