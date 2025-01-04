package com.daarthy.events.model.facades.data;

import com.daarthy.events.Events;
import com.daarthy.events.model.facades.data.components.*;
import com.daarthy.events.model.facades.data.memory.DataMemory;
import com.daarthy.events.model.facades.data.memory.DataMemoryImpl;
import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.persistence.DataSourceLocatorTesting;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.PersistenceContextImpl;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataFacadeTest {
    private static final Long MEMORY_LOOP = 8L;
    private static final Long GUILD_ID = 60L;

    private DataFinderComp dataFinderComp;
    private DataCreationComp dataCreationComp;
    private DataRemovalComp dataRemovalComp;

    @Before
    public void setUp() throws IOException {
        PersistenceContext persistenceContext =
                new PersistenceContextImpl(DataSourceLocatorTesting.getInstance().getDataSource());
        DataMemory memory = DataMemoryImpl.getInstance(MEMORY_LOOP, TimeUnit.MINUTES, persistenceContext);

        dataFinderComp = new DataFinderCompImpl(memory, persistenceContext);
        dataCreationComp = new DataCreationCompImpl(memory, persistenceContext, dataFinderComp);
        dataRemovalComp = new DataRemovalCompImpl(memory, persistenceContext, dataFinderComp);
    }

    @Test
    public void testCreateAndFindPlayer() {
        UUID playerId = UUID.randomUUID();
        dataCreationComp.createPlayer(playerId);
        EventsPlayer eventsPlayer = dataFinderComp.findEventsPlayer(playerId);

        assertEquals(playerId, eventsPlayer.getPlayerId());
        assertEquals(3, eventsPlayer.getMaxMissions(), 0);
        assertEquals(0F, eventsPlayer.getAmpBasicRewards(), 0);
        assertEquals(1L, eventsPlayer.getGuildId(), 0);
    }

    @Test
    public void testCreateGuildAndFindGuildWithDeletion() {
        UUID creator = UUID.randomUUID();
        Long guildId = 60L;
        String kName = "TestGuild";

        dataCreationComp.createPlayer(creator);
        dataCreationComp.createGuild(creator, guildId, kName);

        ExtendedGuild extendedGuild = dataFinderComp.findExtendedGuild(guildId);

        Guild storageGuild = extendedGuild.toStorageGuild();
        assertEquals(kName, storageGuild.getkName());
        assertEquals(0, storageGuild.getLvl(), .0);
        assertEquals(.0, storageGuild.getExperience(), .0);
        assertEquals(storageGuild.getLastTimeUpdated(), LocalDate.now());

        dataRemovalComp.deleteGuild(guildId);

        ExtendedGuild deletedGuild = dataFinderComp.findExtendedGuild(guildId);

        assertNull(deletedGuild);

        EventsPlayer eventsPlayer = dataFinderComp.findEventsPlayer(creator);

        assertEquals(Events.BASIC_GUILD, eventsPlayer.getGuildId(), 0);
    }

    @Test
    public void testCreateGuildAndFindByPersist() {
        UUID creator = UUID.randomUUID();
        String kName = "TestGuild";

        dataCreationComp.createPlayer(creator);
        dataCreationComp.createGuild(creator, GUILD_ID, kName);

        ExtendedGuild createdGuild = dataFinderComp.findExtendedGuild(GUILD_ID);
        createdGuild.addExperience(90F);

        ExtendedGuild extendedGuild = dataFinderComp.findPersistedGuild(GUILD_ID);
        Guild storageGuild = extendedGuild.toStorageGuild();

        assertEquals(kName, storageGuild.getkName());
        assertEquals(0, storageGuild.getLvl(), 0);
        assertEquals(90, storageGuild.getExperience(), .0);
        assertEquals(storageGuild.getLastTimeUpdated(), LocalDate.now());

        dataRemovalComp.deleteGuild(GUILD_ID);
    }

    @Test
    public void testLogoutPlayer() {
        UUID creator = UUID.randomUUID();

        dataCreationComp.createPlayer(creator);
        dataRemovalComp.logOutPlayer(creator);

        Long guildId = dataFinderComp.findExtendedGuild(Events.BASIC_GUILD).toStorageGuild().getId();

        assertEquals(1, guildId, 0);
    }

}
