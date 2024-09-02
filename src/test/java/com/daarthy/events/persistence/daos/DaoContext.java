package com.daarthy.events.persistence.daos;

import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.daos.guild.dao.GuildDao;
import com.daarthy.events.persistence.daos.guild.dao.GuildJdbc;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.dao.MissionAcceptanceDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionAcceptanceJdbc;
import com.daarthy.events.persistence.daos.mission.dao.MissionDao;
import com.daarthy.events.persistence.daos.mission.dao.MissionJdbc;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptKey;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveJdbc;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveProgressDao;
import com.daarthy.events.persistence.daos.objective.dao.ObjectiveProgressJdbc;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import com.daarthy.events.persistence.daos.player.dao.PlayerDao;
import com.daarthy.events.persistence.daos.player.dao.PlayerJdbc;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import com.daarthy.mini.shared.classes.enums.festivals.MissionStatus;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DaoContext {

    private HikariDataSource dataSource;
    private PlayerDao playerDao;
    private GuildDao guildDao;
    private MissionDao missionDao;
    private MissionAcceptanceDao missionAcceptanceDao;
    private ObjectiveDao objectiveDao;
    private ObjectiveProgressDao objectiveProgressDao;

    private List<Long> createdGuildIds = new ArrayList<>();

    public DaoContext() {
        try {
            this.dataSource = SqlConnections.getInstance().getDataSource();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.playerDao = new PlayerJdbc(dataSource);
        this.guildDao = new GuildJdbc(dataSource);
        this.missionDao = new MissionJdbc(dataSource);
        this.missionAcceptanceDao = new MissionAcceptanceJdbc(dataSource);
        this.objectiveDao = new ObjectiveJdbc(dataSource);
        this.objectiveProgressDao = new ObjectiveProgressJdbc(dataSource);
    }

    public PlayerDao playerDao() {
        return playerDao;
    }

    public GuildDao guildDao() {
        return guildDao;
    }

    public MissionDao missionDao() {
        return missionDao;
    }

    public MissionAcceptanceDao missionAcceptanceDao() {
        return missionAcceptanceDao;
    }

    public ObjectiveDao objectiveDao() {
        return objectiveDao;
    }

    public ObjectiveProgressDao objectiveProgressDao() {
        return objectiveProgressDao;
    }

    public EventsPlayer getPlayer(UUID playerId, Long guildId) {
        EventsPlayer player = EventsPlayer.builder()
                .playerId(playerId)
                .ampBasicRewards(0F)
                .maxMissions(1)
                .guildId(guildId)
                .build();
        return playerDao.save(player);
    }

    public Guild getGuild(Long id) {
        Guild guild = Guild.builder()
                .id(id)
                .experience(0.0F)
                .maxLvl(0)
                .kName("kName")
                .ampMissions(0)
                .ampBasicRewards(0.0F)
                .lastTimeUpdated(LocalDate.now())
                .levelUpMod(0.0F)
                .build();
        createdGuildIds.add(id);
        return guildDao.save(guild);
    }

    public Mission getMission(Long guildId, LocalDate expiration) {
        Mission mission = Mission.builder()
                .guildId(guildId)
                .title("title")
                .grade(Grade.A)
                .expiration(expiration)
                .maxCompletions(5)
                .build();
        return missionDao.save(mission);
    }

    public MissionAcceptance getMissionAcceptance(Long missionId, UUID playerId) {
        MissionAcceptance missionAcceptance = MissionAcceptance.builder()
                .key(MissionAcceptKey.builder()
                        .missionId(missionId)
                        .playerId(playerId)
                        .build())
                .status(MissionStatus.ACCEPTED)
                .acceptDate(LocalDate.now().plusDays(1))
                .build();
        return missionAcceptanceDao.save(missionAcceptance);
    }

    public Objective getObjective(Long missionId) {
        Objective objective = Objective.builder()
                .missionId(missionId)
                .actionType(ActionType.KILL)
                .reqAmount(50)
                .target("ZOMBIE")
                .levels(90)
                .build();
        return objectiveDao.save(objective);
    }

    public ObjectiveProgress getObjectiveProgress(UUID playerId, Long objectiveId) {
        ObjectiveProgress objectiveProgress = ObjectiveProgress.builder()
                .key(ObjectiveProgressKey.builder()
                        .objectiveId(objectiveId)
                        .playerId(playerId)
                        .build())
                .build();
        return objectiveProgressDao.save(objectiveProgress);
    }

    public void cleanUp() {
        createdGuildIds.forEach(id -> guildDao.deleteById(id));

        createdGuildIds.clear();
    }

}
