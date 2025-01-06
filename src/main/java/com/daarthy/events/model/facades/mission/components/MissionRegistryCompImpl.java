package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.model.facades.data.structure.ExtendedGuild;
import com.daarthy.events.model.facades.mission.components.validations.PermissionChecker;
import com.daarthy.events.model.facades.mission.components.validations.PermissionCheckerImpl;
import com.daarthy.events.model.facades.mission.memory.MissionMemory;
import com.daarthy.events.model.facades.mission.structure.ExtendedObjective;
import com.daarthy.events.model.facades.mission.structure.PlayerTracker;
import com.daarthy.events.model.result.ResultKey;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptKey;
import com.daarthy.events.persistence.daos.mission.entities.MissionAcceptance;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import com.daarthy.mini.shared.classes.enums.festivals.MissionStatus;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MissionRegistryCompImpl extends MissionAbstractComp implements MissionRegistryComp {
    private final PermissionChecker permissionChecker = new PermissionCheckerImpl();

    public MissionRegistryCompImpl(MissionMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public synchronized StringBuilder joinMission(MiniAuth miniAuth, EventsPlayer eventsPlayer,
                                                  ExtendedGuild extendedGuild, Long missionId) {
        Guild guild = extendedGuild.toStorageGuild();

        MiniCriteria<Mission> missionCriteria = PostgresCriteria.<Mission>builder()
                .selector(FestivalSelector.FIND_MISSION_BY_GUILD)
                .params(List.of(guild.getId(), missionId))
                .resultClass(Mission.class)
                .build();
        Mission mission = persistenceContext.searchDao().findOneByCriteria(missionCriteria);

        StringBuilder result = permissionChecker.checkMission(mission, miniAuth, guild, eventsPlayer,
                persistenceContext);

        if (!result.isEmpty()) {
            return result;
        }

        persistenceContext.missionAcceptanceDao().save(MissionAcceptance.builder()
                .key(MissionAcceptKey.builder()
                        .missionId(missionId)
                        .playerId(miniAuth.getPlayerId())
                        .build())
                .acceptDate(LocalDate.now())
                .status(MissionStatus.ACCEPTED)
                .build());

        MiniCriteria<Objective> objectiveCriteria = PostgresCriteria.<Objective>builder()
                .selector(FestivalSelector.FIND_MISSION_OBJECTIVES)
                .params(List.of(missionId))
                .resultClass(Objective.class)
                .build();

        List<Objective> objectives = persistenceContext.searchDao().findByCriteria(objectiveCriteria);

        for (Objective obj : objectives) {
            memory.addObjective(mission, obj, eventsPlayer.getPlayerId());
        }

        return result.append(persistenceContext.messagesAbstractFactory().toI18Message(miniAuth,
                ResultKey.MISSION_ACCEPTED.name()));

    }

    @Override
    public List<Grade> addProgress(UUID playerId, String target, Integer levels) {

        PlayerTracker playerTracker = memory.getPlayerObjectives().get(playerId);
        Map<ExtendedObjective, ObjectiveProgress> completedObjectives = playerTracker.trackAction(target, levels);

        List<Grade> completedGrades = new ArrayList<>();

        completedObjectives.forEach((key, value) -> {
            memory.removeObjectiveFromCache(key);
            persistenceContext.objectiveProgressDao().save(value);

            Long missionId = key.getObjective().getMissionId();
            if (playerTracker.isMissionCompleted(missionId)) {
                MissionAcceptance acceptance = persistenceContext.missionAcceptanceDao().findById(
                        MissionAcceptKey.builder()
                                .playerId(playerId)
                                .missionId(missionId)
                                .build());
                acceptance.setStatus(MissionStatus.FINALIZED);
                persistenceContext.missionAcceptanceDao().save(acceptance);
                
                completedGrades.add(persistenceContext.missionDao().findById(missionId).getGrade());
            }
        });

        return completedGrades;
    }
}
