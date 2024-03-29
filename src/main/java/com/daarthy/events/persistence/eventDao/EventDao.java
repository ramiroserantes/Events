package com.daarthy.events.persistence.eventDao;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public interface EventDao {

    List<EventData> findCurrentEvents(Connection connection, ScopeEnum scopeEnum);

    int findGuildMedals(Connection connection, Long guildId, Long eventId);

    void saveGuildMedals(Connection connection, Long guildId, Long eventId, int amount);

    Contribution findPlayerContribution(Connection connection, Long eventId, UUID playerId);

    void savePlayerContribution(Connection connection, UUID playerId, Long eventId, Contribution contribution);

}
