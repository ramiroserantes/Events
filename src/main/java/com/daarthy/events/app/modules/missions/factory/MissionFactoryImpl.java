package com.daarthy.events.app.modules.missions.factory;

import com.daarthy.events.Events;
import com.daarthy.events.persistence.mission_dao.Grade;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.mission_dao.ObjectiveData;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class MissionFactoryImpl implements MissionFactory {

    private SecureRandom random = new SecureRandom();

    public HashMap<MissionData, List<ObjectiveData>> getMission(Grade grade, boolean isDefaultGuild) {
        HashMap<MissionData, List<ObjectiveData>> mission = new HashMap<>();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("missions.yml")) {
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, Object>>> yamlData = yaml.load(input);

            if (yamlData.containsKey(grade.toString())) {
                List<Map<String, Object>> missions = yamlData.get(grade.toString());

                if (!missions.isEmpty()) {
                    int randomMissionIndex = random.nextInt(missions.size());
                    Map<String, Object> missionMap = missions.get(randomMissionIndex);

                    MissionData missionData = new MissionData();
                    missionData.setTitle((String) missionMap.get("title"));
                    missionData.setExpiration(LocalDate.from(LocalDate.now().atStartOfDay().plusDays(grade.getPriority())));
                    missionData.setGrade(grade.getGradeString());
                    if (!isDefaultGuild) {
                        missionData.setMaxCompletions(grade.getCompletions());
                    }

                    List<ObjectiveData> objectives = new ArrayList<>();
                    List<Map<String, Object>> objectivesList = (List<Map<String, Object>>) missionMap.get("objectives");

                    for (Map<String, Object> objectiveMap : objectivesList) {
                        ObjectiveData objectiveData = new ObjectiveData();
                        objectiveData.setTarget((String) objectiveMap.get("target"));
                        objectiveData.setActionType(ActionType.valueOf((String) objectiveMap.get("actionType")));

                        int reqAmount = (int) objectiveMap.get("reqAmount");
                        double deviation = random.nextDouble() * 0.2 - 0.1; // Deviation between -10% and 10%
                        objectiveData.setReqAmount((int) Math.round(reqAmount + reqAmount * deviation));

                        if (objectiveMap.containsKey("levels")) {
                            objectiveData.setLevels((int) objectiveMap.get("levels"));
                        }

                        objectives.add(objectiveData);
                    }

                    mission.put(missionData, objectives);
                }
            } else {
                Events.logInfo("There aren't any mission for the declared Grade.");
            }
        } catch (Exception e) {
            Events.logInfo("Source file not Found");
        }

        return mission;
    }

}
