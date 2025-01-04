package com.daarthy.events.model.facades.event.structure;

import com.daarthy.events.model.facades.event.structure.plans.Plan;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.util.Map;

public interface ExtendedEvent {

    /**
     * Adds a player to the event with their initial contribution.
     * This method allows a player to join an event by adding them to the list of players and associating their
     * initial contribution, which includes any items or medals they start with.
     *
     * @param player       the {@link EventsPlayer} object representing the player being added to the event.
     * @param contribution the {@link PlayerContribution} object representing the initial contribution of the player.
     */
    void addPlayer(EventsPlayer player, PlayerContribution contribution);

    /**
     * Applies the event's plan to a specific player's action.
     * This method uses the action type to update a player's contribution to the event, based on the event's plan.
     * The contribution may be modified according to the rules defined in the event's plan.
     *
     * @param player     the {@link EventsPlayer} whose contribution is to be updated.
     * @param actionType the {@link ActionType} representing the player's action in the event.
     * @return the updated {@link PlayerContribution} after the action is applied, or null if the action is not valid.
     */
    PlayerContribution applyPlan(EventsPlayer player, ActionType actionType);

    /**
     * Retrieves the event's data.
     * This method provides detailed information about the event, such as its name, dates, and medal limits.
     *
     * @return the {@link EventData} object containing event details such as the event's start and end dates,
     * maximum medals, and scope.
     */
    EventData getEventData();

    /**
     * Retrieves all players participating in the event.
     * This method returns a map of all players currently participating in the event and their respective contributions.
     *
     * @return a {@link Map} of {@link EventsPlayer} objects to their corresponding {@link PlayerContribution} objects.
     */
    Map<EventsPlayer, PlayerContribution> getPlayers();

    /**
     * Retrieves the plan associated with the event.
     * This method provides access to the event's plan, which defines the rules and logic of how the event progresses
     * and how players' actions affect the event.
     *
     * @return the {@link Plan} object that manages the event's logic.
     */
    Plan getPlan();
}
