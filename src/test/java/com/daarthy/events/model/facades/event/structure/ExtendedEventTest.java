package com.daarthy.events.model.facades.event.structure;

import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.enums.festivals.Scope;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;

public class ExtendedEventTest {

    @Test
    public void testEventByCreation() {
        ExtendedEvent event = new EventAdapter(getEventData());

        assertNotNull(event.getPlan());
        assertNotNull(event.getEventData());
        assertNotNull(event.getPlayers());
    }

    @Test
    public void testEventByAddPlayer() {
        ExtendedEvent event = new EventAdapter(getEventData());
        EventsPlayer player = getPlayer();
        PlayerContribution contribution = getContribution();

        event.addPlayer(player, contribution);

        assertEquals(1, event.getPlayers().size());
    }

    @Test
    public void testEventByApplyPlanNoMatch() {
        ExtendedEvent event = new EventAdapter(getEventData());
        EventsPlayer player = getPlayer();
        PlayerContribution contribution = getContribution();
        event.addPlayer(player, contribution);

        PlayerContribution expectedContribution = event.applyPlan(player, ActionType.MINING);

        assertNull(expectedContribution);
    }

    @Test
    public void testEventByApplyPlanUntilContribution() {
        ExtendedEvent event = new EventAdapter(getEventData());
        EventsPlayer player = getPlayer();
        PlayerContribution contribution = getContribution();
        event.addPlayer(player, contribution);

        PlayerContribution expectedContribution = null;
        while (expectedContribution == null) {
            expectedContribution = event.applyPlan(player, ActionType.KILL);
        }

        assertNotNull(expectedContribution);
    }

    // *****************************************************
    // Métodos internos
    // *****************************************************

    private EventData getEventData() {
        return EventData.builder()
                .scopeEnum(Scope.ALL)
                .name("HuntingEvent")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2L))
                .maxMedals(200)
                .build();
    }

    private PlayerContribution getContribution() {
        return PlayerContribution.builder()
                .items(0)
                .medals(0)
                .build();
    }

    private EventsPlayer getPlayer() {
        return EventsPlayer.builder()
                .playerId(UUID.randomUUID())
                .build();
    }
}
