package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.model.facades.data.structure.GuildAdapter;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.time.LocalDate;
import java.util.UUID;

public class DataCreationCompImpl extends DataAbstractComp implements DataCreationComp {
    private final DataFinderComp dataFinderComp;

    public DataCreationCompImpl(DataMemory memory, PersistenceContext persistenceContext,
                                DataFinderComp dataFinderComp) {
        super(memory, persistenceContext);
        this.dataFinderComp = dataFinderComp;
    }

    @Override
    public EventsPlayer createPlayer(UUID playerId) {
        EventsPlayer player = dataFinderComp.findEventsPlayer(playerId);

        if (player == null) {
            player = EventsPlayer.builder()
                    .playerId(playerId)
                    .build();

            persistenceContext.playerDao().save(player);
            memory.getPlayersData().put(playerId, player);
            ExtendedGuild extendedGuild = memory.getGuildsData().get(player.getGuildId());

            if (extendedGuild == null) {
                memory.getGuildsData().put(player.getGuildId(),
                        GuildAdapter.create(persistenceContext.guildDao().findById(player.getGuildId())));
            }
        }
        return player;
    }

    @Override
    public ExtendedGuild createGuild(UUID creator, Long guildId, String kName) {

        Guild guild = Guild.builder()
                .id(guildId)
                .kName(kName)
                .lastTimeUpdated(LocalDate.now())
                .build();

        memory.getGuildsData().put(guildId, GuildAdapter.create(persistenceContext.guildDao()
                .save(guild)));

        EventsPlayer player = dataFinderComp.findEventsPlayer(creator);
        player.setGuildId(guildId);
        persistenceContext.playerDao().save(player);

        return GuildAdapter.create(guild);
    }
}
