package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.tokens.EventToken;

import java.util.List;

public interface EventActionsComp {

    /**
     * Registers an action performed by a player during the event.
     *
     * <p>This method processes a player's action in the context of all active events. It applies the action according
     * to the event's logic and returns a list of `EventToken` objects associated with the completed actions.</p>
     *
     * @param player     the {@link EventsPlayer} who is performing the action.
     * @param actionType the {@link ActionType} representing the action performed by the player.
     * @return a list of {@link EventToken} objects that represent the outcomes or changes triggered by the player's
     * action.
     */
    List<EventToken> registerAction(EventsPlayer player, ActionType actionType);
}
