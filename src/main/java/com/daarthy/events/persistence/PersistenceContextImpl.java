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
import com.daarthy.events.persistence.factories.messages.MessagesAbstractFactory;
import com.daarthy.events.persistence.factories.messages.MessagesAbstractFactoryImpl;
import com.daarthy.events.persistence.factories.missions.MissionFactory;
import com.daarthy.events.persistence.factories.missions.MissionFactoryImpl;
import com.daarthy.events.persistence.factories.rewards.RewardsFactory;
import com.daarthy.events.persistence.factories.rewards.RewardsFactoryImpl;
import com.zaxxer.hikari.HikariDataSource;

public class PersistenceContextImpl implements PersistenceContext {

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
    private final MessagesAbstractFactory messagesAbstractFactory;
    private final RewardsFactory rewardsFactory;
    private final MissionFactory missionFactory;

    public PersistenceContextImpl(HikariDataSource dataSource) {
        // Dao's
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

        // Factories
        this.messagesAbstractFactory = new MessagesAbstractFactoryImpl();
        this.rewardsFactory = new RewardsFactoryImpl();
        this.missionFactory = new MissionFactoryImpl();
    }

    // *****************************************************
    // Dao's Postgres
    // *****************************************************
    @Override
    public EventDao eventDao() {
        return eventDao;
    }

    @Override
    public GuildMedalsDao guildMedalsDao() {
        return guildMedalsDao;
    }

    @Override
    public PlayerContributionDao playerContributionDao() {
        return playerContributionDao;
    }

    @Override
    public MissionDao missionDao() {
        return missionDao;
    }

    @Override
    public MissionAcceptanceDao missionAcceptanceDao() {
        return missionAcceptanceDao;
    }

    @Override
    public ObjectiveDao objectiveDao() {
        return objectiveDao;
    }

    @Override
    public ObjectiveProgressDao objectiveProgressDao() {
        return objectiveProgressDao;
    }

    @Override
    public PlayerDao playerDao() {
        return playerDao;
    }

    @Override
    public GuildDao guildDao() {
        return guildDao;
    }

    @Override
    public SearchDao searchDao() {
        return searchDao;
    }

    // *****************************************************
    // Factories
    // *****************************************************
    @Override
    public MessagesAbstractFactory messagesAbstractFactory() {
        return messagesAbstractFactory;
    }

    @Override
    public RewardsFactory rewardsFactory() {
        return rewardsFactory;
    }

    @Override
    public MissionFactory missionFactory() {
        return missionFactory;
    }
}
