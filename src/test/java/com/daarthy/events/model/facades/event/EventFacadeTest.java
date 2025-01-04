package com.daarthy.events.model.facades.event;

import com.daarthy.events.persistence.DataSourceLocatorTesting;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.PersistenceContextImpl;
import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;
import com.daarthy.mini.shared.classes.enums.auditable.Language;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.enums.festivals.Scope;
import com.daarthy.mini.shared.classes.tokens.EventToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventFacadeTest {

    private EventFacade eventFacade;
    private EventData eventData;
    private PersistenceTestContext ctx = new PersistenceTestContext();

    @Before
    public void setUp() throws IOException {
        PersistenceContext persistenceContext =
                new PersistenceContextImpl(DataSourceLocatorTesting.getInstance().getDataSource());
        this.eventData = persistenceContext.eventDao().save(
                EventData.builder()
                        .name("HuntingEvent")
                        .scopeEnum(Scope.ALL)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusDays(1))
                        .build());
        this.eventFacade = new EventFacadeImpl(persistenceContext);

        eventFacade.eventMemory().uploadEvents();
    }

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }

    @Test
    public void testEventFacadeInitialization() {
        assertNotNull(eventFacade);
        assertNotNull(eventFacade.lgnComponent());
        assertNotNull(eventFacade.actionsComponent());
        assertNotNull(eventFacade.infoComponent());
    }

    // *****************************************************
    // EventLgnComponent
    // *****************************************************
    @Test
    public void testLgnComponentLogin() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);
        Set<EventData> eventDataSet = eventFacade.infoComponent().getActiveEvents();

        assertFalse(eventDataSet.isEmpty());
        assertTrue(eventDataSet.contains(eventData));
    }

    @Test
    public void testLgnComponentLogoutWithEmptyCache() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);
        eventFacade.lgnComponent().logoutPlayer(player);

        assertTrue(eventFacade.eventMemory().getEvents()
                .stream()
                .noneMatch(extendedEvent -> extendedEvent.getPlayers().containsKey(player)));
        assertTrue(eventFacade.eventMemory().getGuildMedals().isEmpty());
    }

    @Test
    public void testLgnComponentLogoutWithNoEmptyCache() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());
        EventsPlayer player2 = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);
        eventFacade.lgnComponent().loginPlayer(player2);
        eventFacade.lgnComponent().logoutPlayer(player);

        assertFalse(eventFacade.eventMemory().getEvents()
                .stream()
                .noneMatch(extendedEvent -> extendedEvent.getPlayers().containsKey(player2)));
        assertFalse(eventFacade.eventMemory().getGuildMedals().isEmpty());
    }

    @Test
    public void testActionsComponentRegisterAction() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);
        List<EventToken> eventTokens = new ArrayList<>();

        while (eventTokens.isEmpty()) {
            eventTokens = eventFacade.actionsComponent().registerAction(player, ActionType.KILL);
        }

        assertEquals(EventToken.HUNTING_EVENT, eventTokens.get(0));
    }

    @Test
    public void testInfoComponentGetEventInfo() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);
        List<EventToken> eventTokens = new ArrayList<>();

        while (eventTokens.isEmpty()) {
            eventTokens = eventFacade.actionsComponent().registerAction(player, ActionType.KILL);
        }

        StringBuilder info = eventFacade.infoComponent().getEventInfo(player,
                new MiniAuth(player.getPlayerId(), null, Language.EN), eventData.getName());

        assertFalse(info.isEmpty());
    }

    @Test
    public void testInfoComponentGetEventInfoNotAvailable() {
        Guild guild = ctx.getGuild(2L);
        EventsPlayer player = ctx.getPlayer(UUID.randomUUID(), guild.getId());

        eventFacade.lgnComponent().loginPlayer(player);

        StringBuilder info = eventFacade.infoComponent().getEventInfo(player,
                new MiniAuth(player.getPlayerId(), null, Language.ES), "Not Available");

        assertFalse(info.isEmpty());
    }
}
