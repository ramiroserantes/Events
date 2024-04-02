package com.daarthy.events.persistence.guild_dao;

import com.daarthy.events.app.modules.guilds.Guild;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public interface GuildDao {

    Guild createGuild(Long guildId, String kName, Connection connection) throws SQLException;

    void saveGuild(Long guildId, Guild guild, Connection connection) throws SQLException;

    void deleteGuild(Long guildId, Connection connection) throws SQLException;

    Guild findGuildByPlayer(UUID playerId, Connection connection);

    Guild findGuildById(Long guildID, Connection connection);
}
