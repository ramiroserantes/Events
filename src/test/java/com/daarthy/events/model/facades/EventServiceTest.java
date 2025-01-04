package com.daarthy.events.model.facades;

public class EventServiceTest {

  /*  private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private EventDao eventDao = new EventJdbc();
    //private PlayerDao playerDao = new PlayerJdbc();
    private GuildDao guildDao = new GuildJdbc();
    private EventService eventService = new EventServiceImpl(eventDao, dataSource);
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    public EventServiceTest() throws IOException {
    }

    @Test
    public void testEventServiceByActiveEvents() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<Event> eventList = eventService.getActiveEvents();

        assertEquals(3, eventList.size());

    }

    @Test
    public void testEventServiceByInfo() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        StringBuilder result1 = eventService.getEventInfo(playerId, "GatheringEvent");
        StringBuilder result2 = eventService.getEventInfo(playerId, "HuntingEvent");
        StringBuilder result3 = eventService.getEventInfo(playerId, "MiningEvent");

        assertFalse(result1.toString().isEmpty());
        assertFalse(result2.toString().isEmpty());
        assertFalse(result3.toString().isEmpty());

    }

    @Test
    public void testEventServiceByNoInfo() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        StringBuilder result1 = eventService.getEventInfo(playerId, "Gant");

        assertNotNull(result1.toString());

    }


    @Test
    public void testEventServiceByRemoveEvents() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventData> eventData = eventService.removeInactiveEvents();

        assertTrue(eventData.isEmpty());

    }

    @Test
    public void testEventServiceByAddProgress() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();
        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.isEmpty()) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.FARM));
        }

        assertFalse(false);

    }

    @Test
    public void testEventServiceByAddProgressAndSave() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.isEmpty()) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.FARM));
        }

        eventService.removePlayer(playerId);
        eventService.saveAllGuilds();
        eventService.removeGuild(1L);

        eventService.initPlayer(playerId, 1L);

        assertTrue(true);

    }

    @Test
    public void testEventServiceByAddProgressByLimitMedals() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.size() < 208) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.KILL));
        }

        assertEquals(200, eventService.getGuildMedals(1L).getMedals().get(2L), 0.0);

    }*/
}
