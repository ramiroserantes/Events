package com.daarthy.events.persistence;

import com.daarthy.events.persistence.daos.SearchDao;
import com.daarthy.events.persistence.daos.SearchJdbc;
import com.daarthy.events.persistence.daos.event.dao.*;
import com.daarthy.events.persistence.daos.guild.dao.GuildDao;
import com.daarthy.events.persistence.daos.guild.dao.GuildJdbc;
import com.daarthy.events.persistence.daos.mission.dao.MissionAcceptanceDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionAcceptanceJdbc;
import com.daarthy.events.persistence.daos.mission.dao.MissionDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionJdbc;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveJdbc;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveProgressDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveProgressJdbc;
import com.daarthy.events.persistence.daos.player.dao.PlayerDao;
import com.daarthy.events.persistence.daos.player.dao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;

public class PersistenceContextImpl {

    // *****************************************************
    // Dao's Postgres
    // *****************************************************

    // Events Domain
    private final EventDao eventDao;
    private final GuildMedalsDao guildMedalsDao;
    private final PlayerContributionDao playerContributionDao;

    // Missions Domain
    private final MissionDao missionDao;
    private final MissionAcceptanceDao missionAcceptanceDao;

    // Objectives Domain
    private final ObjectiveDao objectiveDao;
    private final ObjectiveProgressDao objectiveProgressDao;

    // Players Domain
    private final PlayerDao playerDao;

    // Guilds Domain
    private final GuildDao guildDao;

    // General Search Domain
    private final SearchDao searchDao;

    // *****************************************************
    // Factories
    // *****************************************************

    public PersistenceContextImpl(HikariDataSource dataSource) {
        this.eventDao = new EventJdbc(dataSource);
        this.guildMedalsDao = new GuildMedalsJdbc(dataSource);
        this.playerContributionDao = new PlayerContributionJdbc(dataSource);

        this.missionDao = new MissionJdbc(dataSource);
        this.missionAcceptanceDao = new MissionAcceptanceJdbc(dataSource);

        this.objectiveDao = new ObjectiveJdbc(dataSource);
        this.objectiveProgressDao = new ObjectiveProgressJdbc(dataSource);

        this.playerDao = new PlayerJdbc(dataSource);

        this.guildDao = new GuildJdbc(dataSource);

        this.searchDao = new SearchJdbc(dataSource);
    }

    // *****************************************************
    // Dao's Postgres
    // *****************************************************
    public EventDao eventDao() {
        return eventDao;
    }

    public GuildMedalsDao guildMedalsDao() {
        return guildMedalsDao;
    }

    public PlayerContributionDao playerContributionDao() {
        return playerContributionDao;
    }

    public MissionDao missionDao() {
        return missionDao;
    }

    public MissionAcceptanceDao missionAcceptanceDao() {
        return missionAcceptanceDao;
    }

    public ObjectiveDao objectiveDao() {
        return objectiveDao;
    }

    public ObjectiveProgressDao objectiveProgressDao() {
        return objectiveProgressDao;
    }

    public PlayerDao playerDao() {
        return playerDao;
    }

    public GuildDao guildDao() {
        return guildDao;
    }

    public SearchDao searchDao() {
        return searchDao;
    }
}
