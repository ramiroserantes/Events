package com.daarthy.events.persistence.factories.missions;

import com.daarthy.events.Events;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import com.daarthy.mini.shared.classes.yaml.AbstractMiniYaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class MissionFactoryImpl extends AbstractMiniYaml implements MissionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MissionFactoryImpl.class);
    private static final String FILE = "configuration/missions.yml";
    private static final String TITLE_ATTRIBUTE = "title";
    private static final String OBJECTIVE_ATTRIBUTE = "objectives";
    private static final String TARGET_ATTRIBUTE = "target";
    private static final String ACTION_TYPE_ATTRIBUTE = "actionType";
    private static final String REQUIRED_AMOUNT_ATTRIBUTE = "reqAmount";
    private static final String LEVEL_ATTRIBUTE = "levels";
    private final SecureRandom random = new SecureRandom();

    public MissionFactoryImpl() {
        super(FILE);
    }

    public Map<Mission, List<Objective>> getMission(Grade grade, boolean isDefaultGuild, Set<String> usedMissions) {
        return processMission(grade, filterAvailableMissions(grade, usedMissions), isDefaultGuild, usedMissions);
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    private List<Map<String, Object>> filterAvailableMissions(Grade grade, Set<String> usedMissions) {
        List<Map<String, Object>> availableMissions = new ArrayList<>();
        List<Map<String, Object>> missionsRoot = getMissionsRoot(grade);
        for (Map<String, Object> missionMap : missionsRoot) {
            String missionTitle = (String) missionMap.get(TITLE_ATTRIBUTE);
            if (!usedMissions.contains(missionTitle)) {
                availableMissions.add(missionMap);
            }
        }
        return availableMissions;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getMissionsRoot(Grade grade) {
        if (yamlFile.containsKey(grade.toString())) {
            return (List<Map<String, Object>>) yamlFile.get(grade.toString());
        } else {
            LOGGER.error(Events.MICRO_NAME + "- Error in retrieving the mission grade {}",
                    grade.getValue());
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Mission, List<Objective>> processMission(Grade grade, List<Map<String, Object>> availableMissions,
                                                         boolean isDefaultGuild, Set<String> usedMissions) {
        Map<Mission, List<Objective>> missionExtract = new HashMap<>();

        if (!availableMissions.isEmpty()) {
            int randomMissionIndex = random.nextInt(availableMissions.size());
            Map<String, Object> missionMap = availableMissions.get(randomMissionIndex);

            // @formatter:off
            Mission mission = Mission.builder()
                    .title((String) missionMap.get(TITLE_ATTRIBUTE))
                    .expiration(LocalDate.from(LocalDate.now().atStartOfDay().plusDays(grade.getPriority())))
                    .grade(grade)
                    .maxCompletions(isDefaultGuild ? null : grade.getCompletions())
                    .build();
            // @formatter:on

            List<Objective> objectives = new ArrayList<>();
            List<Map<String, Object>> objectivesList = (List<Map<String, Object>>) missionMap.get(OBJECTIVE_ATTRIBUTE);

            for (Map<String, Object> objectiveMap : objectivesList) {

                int reqAmount = (int) objectiveMap.get(REQUIRED_AMOUNT_ATTRIBUTE);
                double deviation = random.nextDouble() * 0.2 - 0.1; // Deviation between -10% and 10%

                // @formatter:off
                objectives.add(Objective.builder()
                        .target((String) objectiveMap.get(TARGET_ATTRIBUTE))
                        .actionType(ActionType.valueOf((String) objectiveMap.get(ACTION_TYPE_ATTRIBUTE)))
                        .requiredAmount((int) Math.round(reqAmount + reqAmount * deviation))
                        .levels(objectiveMap.containsKey(LEVEL_ATTRIBUTE) ? (int) objectiveMap.get(LEVEL_ATTRIBUTE)
                                : null)
                        .build()
                );
                // @formatter:on
            }

            missionExtract.put(mission, objectives);
            usedMissions.add(mission.getTitle());
        } else {
            LOGGER.info(Events.MICRO_NAME + "- There are no missions available for this grade {} ",
                    grade.getValue());
        }
        return missionExtract;
    }


}
