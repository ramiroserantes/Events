package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.model.facades.data.structure.GuildAdapter;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.UUID;

public class DataFinderCompImpl extends DataAbstractComp implements DataFinderComp {

    public DataFinderCompImpl(DataMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public EventsPlayer findEventsPlayer(UUID playerId) {
        return memory.getPlayersData().computeIfAbsent(playerId, id ->
                persistenceContext.playerDao().findById(id)
        );
    }

    @Override
    public ExtendedGuild findExtendedGuild(Long guildId) {
        return memory.getGuildsData().computeIfAbsent(guildId, id ->
                GuildAdapter.create(persistenceContext.guildDao().findById(id))
        );
    }

    @Override
    public ExtendedGuild findPersistedGuild(Long guildId) {
        return memory.getGuildsData().getOrDefault(guildId,
                GuildAdapter.create(persistenceContext.guildDao().findById(guildId)));
    }
}
