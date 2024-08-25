package com.daarthy.events.app.services;

import com.daarthy.events.persistence.daos.mission.CompletionData;
import com.daarthy.events.persistence.daos.mission.MissionDao;
import com.daarthy.events.persistence.daos.mission.MissionData;
import com.daarthy.events.persistence.daos.mission.ObjectiveData;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MissionInfoServiceImpl implements MissionInfoService {
/*
    private MissionDao missionDao;
    private HikariDataSource dataSource;

    public MissionInfoServiceImpl(MissionDao missionDao, HikariDataSource dataSource) {
        this.missionDao = missionDao;
        this.dataSource = dataSource;
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> findGuildDashBoard(Long guildId) {

        Map<MissionData, List<ObjectiveData>> objec = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {

            List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);

            missionDataList.forEach(item ->
                    objec.put(item, missionDao.findMissionObjectivesByGuild(item.getMissionId(), connection))
            );

        } catch (SQLException e) {
            //  Events.logInfo("Error on Guild Dashboard.");
        }

        return objec;
    }

    @Override
    public Map<String, CompletionData> getPlayerRates(UUID playerId) {

        Map<String, CompletionData> completions = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {

            completions = missionDao.findPlayerCompletionsRate(playerId, connection);

        } catch (SQLException e) {
            //   Events.logInfo("Rates not found");
        }
        return completions;
    }*/
}
