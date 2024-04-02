package com.daarthy.events.app.modules.events;

import com.daarthy.events.persistence.event_dao.Contribution;
import com.daarthy.events.persistence.event_dao.EventData;
import com.daarthy.events.persistence.event_dao.ScopeEnum;
import com.daarthy.events.persistence.mission_dao.ActionType;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventTest {

    private EventData getEventData() {
        return new EventData(ScopeEnum.ALL, "HuntingEvent", LocalDate.now(),
                LocalDate.now().plusDays(2L), 200);
    }

    @Test
    public void testEventByCreation() {

        Event event = new EventImpl(getEventData());

        assertNotNull(event.getPlan());
        assertNotNull(event.getData());
        assertNotNull(event.getPlayers());

    }

    @Test
    public void testEventByAddPlayer() {

        Event event = new EventImpl(getEventData());
        UUID playerId = UUID.randomUUID();
        Contribution contribution = new Contribution();

        event.addPlayer(playerId, contribution);

        assertEquals(1, event.getPlayers().size());

    }


    @Test
    public void testEventByApplyPlanNoMatch() {

        Event event = new EventImpl(getEventData());
        UUID playerId = UUID.randomUUID();
        Contribution contribution = new Contribution();
        event.addPlayer(playerId, contribution);

        Contribution expectedContribution = event.applyPlan(playerId, ActionType.MINING);

        assertNull(expectedContribution);

    }

    @Test
    public void testEventByApplyPlanUntilContribution() {

        Event event = new EventImpl(getEventData());
        UUID playerId = UUID.randomUUID();
        Contribution contribution = new Contribution();
        event.addPlayer(playerId, contribution);

        Contribution expectedContribution = null;

        while (expectedContribution == null) {
            expectedContribution = event.applyPlan(playerId, ActionType.KILL);
        }

        assertTrue(true);

    }

    @Test
    public void testEventByRemovePlayer() {

        Event event = new EventImpl(getEventData());
        UUID playerId = UUID.randomUUID();
        Contribution contribution = new Contribution();
        event.addPlayer(playerId, contribution);

        Contribution expectedContribution = null;

        while (expectedContribution == null) {
            expectedContribution = event.applyPlan(playerId, ActionType.KILL);
        }

        Contribution received = event.removePlayer(playerId);

        assertEquals(received, expectedContribution);

    }

}
