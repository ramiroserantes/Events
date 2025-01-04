package com.daarthy.events.model.facades.data.memory;

import com.daarthy.events.Events;
import com.daarthy.events.model.facades.data.structure.GuildAdapter;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.processor.MiniProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class DataMemoryImpl extends MiniProcessor implements DataMemory {
    private static final AtomicReference<DataMemoryImpl> instance = new AtomicReference<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DataMemoryImpl.class);
    private static final int EXPECTED_POOL = 1;
    private final Map<UUID, EventsPlayer> playersData = new ConcurrentHashMap<>();
    private final Map<Long, GuildAdapter> guildsData = new ConcurrentHashMap<>();
    private final PersistenceContext persistenceContext;

    private DataMemoryImpl(long loopTime, TimeUnit timeUnit, PersistenceContext persistenceContext) {
        super(loopTime, timeUnit, EXPECTED_POOL);
        this.persistenceContext = persistenceContext;
    }

    // Memory items must be Singleton's
    public static DataMemoryImpl getInstance(long loopTime, TimeUnit timeUnit, PersistenceContext persistenceContext) {
        return instance.updateAndGet(existingInstance ->
                existingInstance != null ? existingInstance : new DataMemoryImpl(loopTime, timeUnit, persistenceContext)
        );
    }

    @Override
    protected void sync() {
        LOGGER.info(Events.MICRO_NAME + "- Starting DataMemory sync of the DataFacade");

        persistenceContext.playerDao().saveAll(playersData.values());
        persistenceContext.guildDao().saveAll(guildsData.values()
                .stream()
                .map(GuildAdapter::toStorageGuild)
                .toList()
        );
        removeUnusedGuilds();

        LOGGER.info(Events.MICRO_NAME + "- Done DataMemory sync of the DataFacade");
    }

    @Override
    public void removePlayer(UUID playerId) {
        EventsPlayer cachePlayer = playersData.get(playerId);
        if (cachePlayer != null) {
            persistenceContext.playerDao().save(cachePlayer);
            playersData.remove(playerId);
        }
    }

    @Override
    public void removeUnusedGuilds() {
        Set<Long> usedGuildIds = playersData.values().stream()
                .map(EventsPlayer::getGuildId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Iterator<Map.Entry<Long, GuildAdapter>> iterator = guildsData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, GuildAdapter> entry = iterator.next();
            Long guildId = entry.getKey();
            GuildAdapter guildAdapter = entry.getValue();

            if (!usedGuildIds.contains(guildId)) {
                persistenceContext.guildDao().save(guildAdapter.toStorageGuild());
                iterator.remove();
            }
        }
    }

    @Override
    public void removeAllPlayersFromGuild(Long guildId) {
        playersData.values().forEach(player -> {
            if (guildId.equals(player.getGuildId())) {
                player.setGuildId(Events.BASIC_GUILD);
            }
        });
        removeUnusedGuilds();
    }

    @Override
    public Map<UUID, EventsPlayer> getPlayersData() {
        return playersData;
    }

    @Override
    public Map<Long, GuildAdapter> getGuildsData() {
        return guildsData;
    }

}
