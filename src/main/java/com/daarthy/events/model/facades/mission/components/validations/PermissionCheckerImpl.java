package com.daarthy.events.model.facades.mission.components.validations;

import com.daarthy.events.model.result.ResultKey;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;

import java.time.LocalDate;
import java.util.List;

public class PermissionCheckerImpl implements PermissionChecker {

    @Override
    public StringBuilder checkMission(Mission mission, MiniAuth miniAuth, Guild guild,
                                      EventsPlayer eventsPlayer, PersistenceContext persistenceContext) {
        StringBuilder result = new StringBuilder();

        if (mission.getExpiration().isBefore(LocalDate.now())) {
            return result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                    ResultKey.EXPIRED_MISSION.name()));
        }

        MiniCriteria<Boolean> acceptedCriteria = PostgresCriteria.<Boolean>builder()
                .selector(FestivalSelector.WAS_MISSION_ACCEPTED_BY_PLAYER)
                .params(List.of(mission.getId(), miniAuth.getPlayerId()))
                .resultClass(Boolean.class)
                .build();

        if (persistenceContext.searchDao().findOneByCriteria(acceptedCriteria)) {
            return result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                    ResultKey.ALREADY_ACCEPTED_MISSION.name()));
        }

        MiniCriteria<Integer> missionsAcceptedCriteria = PostgresCriteria.<Integer>builder()
                .selector(FestivalSelector.ACCEPTED_MISSIONS_TODAY_BY_PLAYER)
                .params(List.of(miniAuth.getPlayerId()))
                .resultClass(Integer.class)
                .build();

        Integer numberOfMissionsAccepted = persistenceContext.searchDao().findOneByCriteria(missionsAcceptedCriteria);

        if (numberOfMissionsAccepted >= eventsPlayer.getMaxMissions()) {
            return result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                    ResultKey.MAX_MISSIONS_REACHED_TODAY.name()));
        }

        if (mission.availablePlayerAddition(guild.getAmpMissions())) {
            return result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                    ResultKey.MAX_MISSIONS_CAPACITY.name()));
        }

        return result;
    }
}
