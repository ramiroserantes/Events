package com.daarthy.events.api;

import com.daarthy.events.Events;
import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.daarthy.events.persistence.mission_dao.CompletionData;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.mission_dao.ObjectiveData;
import com.daarthy.events.persistence.player_dao.PlayerData;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ApiGatewayImpl implements ApiGateway{

    private final AppContainer container;

    public ApiGatewayImpl(HikariDataSource dataSource) {
        this.container = new AppContainer(dataSource);
    }

    @Override
    public void logInRequest(UUID playerId) {

        container.getDataService().initPlayer(playerId);
        PlayerData playerData = container.getDataService().getPlayerData(playerId);
        Guild guild = container.getDataService().getGuild(playerData.getGuildId());

        container.getMissionFunctionalService().fillGuildDashBoard(playerData.getGuildId(), guild);
        container.getMissionFunctionalService().initPlayer(playerId);
        if(!Objects.equals(playerData.getGuildId(), Events.getBasicGuildId())) {
            container.getEventService().initPlayer(playerId, playerData.getGuildId());
        }
    }

    @Override
    public void logOutRequest(UUID playerId) {

        PlayerData playerData = container.getDataService().getPlayerData(playerId);
        container.getDataService().removePlayer(playerId);
        container.getDataService().removeGuild(playerData.getGuildId());

        container.getMissionFunctionalService().removePlayer(playerId);
        Guild guild = container.getDataService().getGuild(playerData.getGuildId());

        if(guild == null && !Objects.equals(playerData.getGuildId(), Events.getBasicGuildId())) {
            container.getEventService().removeGuild(playerData.getGuildId());
            container.getEventService().removePlayer(playerId);
        }
    }

    @Override
    public PlayerData getPlayerDataRequest(UUID playerId) {
        return container.getDataService().getPlayerData(playerId);
    }

    @Override
    public Guild getGuildDataRequest(UUID playerId) {
        return container.getDataService().getGuild(container
                .getDataService().getPlayerData(playerId).getGuildId());
    }

    @Override
    public void deleteGuildRequest(Long guildId) {
        container.getEventService().removeGuild(guildId);
        container.getDataService().deleteGuild(guildId);
    }

    @Override
    public void createGuildRequest(UUID playerId, Long guildId, String kName) {
        container.getDataService().createGuild(playerId, guildId, kName);
        Guild guild = container.getDataService().getGuild(guildId);
        container.getMissionFunctionalService().fillGuildDashBoard(guildId, guild);
        container.getEventService().removePlayer(playerId);
        container.getEventService().initPlayer(playerId, guildId);
    }

    @Override
    public void savePlayerRequest(UUID playerId) {
        container.getDataService().savePlayer(playerId);
    }

    @Override
    public List<EventToken> eventActivityRequest(UUID playerId, ActionType actionType) {

        PlayerData playerData = container.getDataService().getPlayerData(playerId);
        return container.getEventService().registerAction(playerId, playerData.getGuildId(), actionType);
    }

    @Override
    public List<StringBuilder> getActiveEventsRequest() {
        return container.getEventService().getActiveEvents();
    }

    @Override
    public StringBuilder getEventInfoRequest(UUID playerId, String eventName) {

        PlayerData playerData = container.getDataService().getPlayerData(playerId);

        if(Objects.equals(playerData.getGuildId(), Events.getBasicGuildId())) {
            return new StringBuilder("You cant participate in this event when you are in the Server guild.");
        }

        return container.getEventService().getEventInfo(playerId, eventName);
    }

    @Override
    public int getGuildMedalsOnEventRequest(UUID playerId, Long eventId) {

        PlayerData playerData = container.getDataService().getPlayerData(playerId);

        return container.getEventService().getGuildMedals(playerData.getGuildId()).getEventMedals(eventId);
    }

    @Override
    public StringBuilder joinMissionRequest(UUID playerId, Long missionId) {

        PlayerData playerData = container.getDataService().getPlayerData(playerId);
        Guild guild = container.getDataService().getGuild(playerData.getGuildId());

        return container.getMissionFunctionalService().joinMission(playerId, playerData, guild, missionId);
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> getPlayerDashBoardRequest(UUID playerId) {
        return container.getMissionFunctionalService().findPlayerDashBoard(playerId);
    }

    @Override
    public Map<MissionData, List<ObjectiveData>> getGuildDashBoardRequest(UUID playerId) {
        PlayerData playerData = container.getDataService().getPlayerData(playerId);
        return container.getMissionInfoService().findGuildDashBoard(playerData.getGuildId());
    }

    @Override
    public Map<String,CompletionData> getPlayerRatesRequest(UUID playerId) {
        return container.getMissionInfoService().getPlayerRates(playerId);
    }

    @Override
    public AppContainer getContainer() {
        return container;
    }
}
