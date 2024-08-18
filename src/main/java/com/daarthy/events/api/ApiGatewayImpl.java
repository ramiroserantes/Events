package com.daarthy.events.api;


public abstract class ApiGatewayImpl implements ApiGateway{
/*
    private final AppContainer container;

    public ApiGatewayImpl(HikariDataSource dataSource) {
        this.container = new AppContainer(dataSource);
    }

    @Override
    public void logInRequest(UUID playerId) {

        container.getDataService().initPlayer(playerId);
        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);
        Guild guild = container.getDataService().getGuild(eventsPlayer.getGuildId());

        container.getMissionFunctionalService().fillGuildDashBoard(eventsPlayer.getGuildId(), guild);
        container.getMissionFunctionalService().initPlayer(playerId);
        container.getEventService().setUpEvents();
        if(!Objects.equals(eventsPlayer.getGuildId(), Events.getBasicGuildId())) {
            container.getEventService().initPlayer(playerId, eventsPlayer.getGuildId());
        }
    }

    @Override
    public void logOutRequest(UUID playerId) {

        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);
        container.getDataService().removePlayer(playerId);
        container.getDataService().removeGuild(eventsPlayer.getGuildId());

        container.getMissionFunctionalService().removePlayer(playerId);
        Guild guild = container.getDataService().getGuild(eventsPlayer.getGuildId());

        if(guild == null && !Objects.equals(eventsPlayer.getGuildId(), Events.getBasicGuildId())) {
            container.getEventService().removeGuild(eventsPlayer.getGuildId());
            container.getEventService().removePlayer(playerId);
        }
    }

    @Override
    public EventsPlayer getPlayerDataRequest(UUID playerId) {
        return container.getDataService().getPlayerData(playerId);
    }

    @Override
    public Guild getGuildDataRequest(UUID playerId) {
        return container.getDataService().getGuild(container
                .getDataService().getPlayerData(playerId).getGuildId());
    }

    @Override
    public int getMaxJobLevelRequest(UUID playerId) {
        return container.getDataService().getJobMaxLevel(playerId);
    }

    @Override
    public void deleteGuildRequest(Long guildId) {
        container.getEventService().removeGuild(guildId);
        container.getDataService().deleteGuild(guildId);
    }

    @Override
    public void createGuildRequest(UUID playerId, Long guildId, String kName) {
      /*  container.getDataService().createGuild(playerId, guildId, kName);
        Guild guild = container.getDataService().getGuild(guildId);
        container.getMissionFunctionalService().fillGuildDashBoard(guildId, guild);
        container.getEventService().removePlayer(playerId);
        container.getEventService().initPlayer(playerId, guildId);
    *//*}

    @Override
    public void savePlayerRequest(UUID playerId) {
        container.getDataService().savePlayer(playerId);
    }

    @Override
    public List<EventToken> eventActivityRequest(UUID playerId, ActionType actionType) {

        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);

        if(eventsPlayer.getGuildId().equals(Events.getBasicGuildId())) {
            return null;
        }

        return container.getEventService().registerAction(playerId, eventsPlayer.getGuildId(), actionType);
    }

    @Override
    public List<Event> getActiveEventsRequest() {
        return container.getEventService().getActiveEvents();
    }

    @Override
    public StringBuilder getEventInfoRequest(UUID playerId, String eventName) {

        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);

        if(Objects.equals(eventsPlayer.getGuildId(), Events.getBasicGuildId())) {
            return new StringBuilder(">> You cant participate in this event when you are in the Server guild.");
        }

        return container.getEventService().getEventInfo(playerId, eventName);
    }

    @Override
    public int getGuildMedalsOnEventRequest(UUID playerId, Long eventId) {

        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);

        return container.getEventService().getGuildMedals(eventsPlayer.getGuildId()).getEventMedals(eventId);
    }

    @Override
    public List<Grade> missionActivityRequest(UUID playerId, String target, Integer level) {

        return container.getMissionFunctionalService().addProgress(playerId, target, level);
    }

    @Override
    public StringBuilder joinMissionRequest(UUID playerId, Long missionId) {

        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);
        Guild guild = container.getDataService().getGuild(eventsPlayer.getGuildId());

        return container.getMissionFunctionalService().joinMission(playerId, eventsPlayer, guild, missionId);
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> getPlayerDashBoardRequest(UUID playerId) {
        return container.getMissionFunctionalService().findPlayerDashBoard(playerId);
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> getGuildDashBoardRequest(UUID playerId) {
        EventsPlayer eventsPlayer = container.getDataService().getPlayerData(playerId);
        return container.getMissionInfoService().findGuildDashBoard(eventsPlayer.getGuildId());
    }

    @Override
    public Map<String,CompletionData> getPlayerRatesRequest(UUID playerId) {
        return container.getMissionInfoService().getPlayerRates(playerId);
    }

    @Override
    public AppContainer getContainer() {
        return container;
    }*/
}
