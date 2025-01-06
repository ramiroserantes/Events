package com.daarthy.events.model.facades;

public class MissionInfoServiceImpl implements MissionInfoService {
/*
    private MissionDao missionDao;
    private HikariDataSource dataSource;

    public MissionInfoServiceImpl(MissionDao missionDao, HikariDataSource dataSource) {
        this.missionDao = missionDao;
        this.dataSource = dataSource;
    }

    @Override // Ok
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
