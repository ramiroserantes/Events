package com.daarthy.events.persistence.guildDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public interface GuildDao {

    GuildData createGuild(Long guildId, String kName, Connection connection) throws SQLException;

    void saveGuild(GuildData guildData, Connection connection) throws SQLException;

    void deleteGuild(Long guildId, Connection connection) throws SQLException;

    GuildData findGuildByPlayer(UUID playerId, Connection connection);

    Long findDefaultGuildId(Connection connection);

}
