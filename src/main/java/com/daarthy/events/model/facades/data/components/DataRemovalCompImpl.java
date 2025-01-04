package com.daarthy.events.model.facades.data.components;

import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

import java.util.UUID;

public class DataRemovalCompImpl extends DataAbstractComp implements DataRemovalComp {
    private final DataFinderComp dataFinderComp;

    public DataRemovalCompImpl(DataMemory memory, PersistenceContext persistenceContext,
                               DataFinderComp dataFinderComp) {
        super(memory, persistenceContext);
        this.dataFinderComp = dataFinderComp;
    }

    @Override
    public void logOutPlayer(UUID playerId) {
        EventsPlayer cachePlayer = dataFinderComp.findEventsPlayer(playerId);
        persistenceContext.playerDao().save(cachePlayer);
        memory.getPlayersData().remove(playerId);
        memory.removeUnusedGuilds();
    }

    @Override
    public void deleteGuild(Long guildId) {
        memory.removeAllPlayersFromGuild(guildId);
        persistenceContext.playerDao().saveAll(memory.getPlayersData().values());
        persistenceContext.guildDao().deleteById(guildId);
    }
}
