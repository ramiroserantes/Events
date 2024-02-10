package com.daarthy.events.persistence.eventDao;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public interface EventDao {

    List<EventData> findCurrentEvents(Connection connection, ScopeEnum scopeEnum);

    List<TaskData> findEventTasks(Connection connection, Long eventId);

    int findGuildMedals(Connection connection, Long guildId, Long eventId);

    void saveGuildMedals(Connection connection, Long guildId, Long eventId, int amount);

    int findPlayerContribution(Connection connection, Long taskId, UUID playerId);

    void savePlayerContribution(Connection connection, UUID playerId, Long taskId, int contribution);

}
