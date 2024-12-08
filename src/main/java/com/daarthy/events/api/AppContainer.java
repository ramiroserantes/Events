package com.daarthy.events.api;

public class AppContainer {

    /*private final EventDao eventDao = new EventJdbc();
    private final GuildDao guildDao = new GuildJdbc();
    private final MissionDao missionDao = new MissionJdbc();
   // private final PlayerDao playerDao = new PlayerJdbc();

    //private final DataService dataService;
    private final EventService eventService;
    private final MissionInfoService missionInfoService;
   // private final MissionFunctionalService missionFunctionalService;

    public AppContainer(HikariDataSource dataSource) {
      //  dataService = new DataServiceImpl(dataSource, playerDao, guildDao);
        eventService = new EventServiceImpl(eventDao, dataSource);
        missionInfoService = new MissionInfoServiceImpl(missionDao, dataSource);
       // missionFunctionalService = new MissionFunctionalServiceImpl(dataSource, missionDao);
    }

    /*public DataService getDataService() {
        return dataService;
    }*/

   /* public EventService getEventService() {
        return eventService;
    }

    public MissionInfoService getMissionInfoService() {
        return missionInfoService;
    }

   /* public MissionFunctionalService getMissionFunctionalService() {
        return missionFunctionalService;
    }*/
}
