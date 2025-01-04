package com.daarthy.events.persistence;

import com.daarthy.events.persistence.daos.SearchDao;
import com.daarthy.events.persistence.daos.event.dao.EventDao;
import com.daarthy.events.persistence.daos.event.dao.GuildMedalsDao;
import com.daarthy.events.persistence.daos.event.dao.PlayerContributionDao;
import com.daarthy.events.persistence.daos.guild.dao.GuildDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionAcceptanceDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveProgressDao;
import com.daarthy.events.persistence.daos.player.dao.PlayerDao;
import com.daarthy.events.persistence.factories.messages.MessagesAbstractFactory;
import com.daarthy.events.persistence.factories.missions.MissionFactory;
import com.daarthy.events.persistence.factories.rewards.RewardsFactory;

public interface PersistenceContext {

    // *****************************************************
    // Dao's Postgres
    // *****************************************************

    /**
     * Retrieves the DAO for event-related operations.
     *
     * @return EventDao for managing events.
     */
    EventDao eventDao();

    /**
     * Retrieves the DAO for guild medal-related operations.
     *
     * @return GuildMedalsDao for managing guild medals.
     */
    GuildMedalsDao guildMedalsDao();

    /**
     * Retrieves the DAO for player contribution-related operations.
     *
     * @return PlayerContributionDao for managing player contributions.
     */
    PlayerContributionDao playerContributionDao();

    /**
     * Retrieves the DAO for mission-related operations.
     *
     * @return MissionDao for managing missions.
     */
    MissionDao missionDao();

    /**
     * Retrieves the DAO for mission acceptance-related operations.
     *
     * @return MissionAcceptanceDao for managing mission acceptances.
     */
    MissionAcceptanceDao missionAcceptanceDao();

    /**
     * Retrieves the DAO for objective-related operations.
     *
     * @return ObjectiveDao for managing objectives.
     */
    ObjectiveDao objectiveDao();

    /**
     * Retrieves the DAO for objective progress-related operations.
     *
     * @return ObjectiveProgressDao for managing objective progress.
     */
    ObjectiveProgressDao objectiveProgressDao();

    /**
     * Retrieves the DAO for player-related operations.
     *
     * @return PlayerDao for managing player data.
     */
    PlayerDao playerDao();

    /**
     * Retrieves the DAO for guild-related operations.
     *
     * @return GuildDao for managing guild data.
     */
    GuildDao guildDao();

    /**
     * Retrieves the DAO for search-related operations.
     *
     * @return SearchDao for managing search functionalities.
     */
    SearchDao searchDao();

    // *****************************************************
    // Factories
    // *****************************************************

    /**
     * Retrieves the factory for message-related operations.
     *
     * @return MessagesAbstractFactory for creating message-related objects.
     */
    MessagesAbstractFactory messagesAbstractFactory();

    /**
     * Retrieves the factory for reward-related operations.
     *
     * @return RewardsFactory for creating reward-related objects.
     */
    RewardsFactory rewardsFactory();

    /**
     * Retrieves the factory for mission-related operations.
     *
     * @return MissionFactory for creating mission-related objects.
     */
    MissionFactory missionFactory();
}
