package com.daarthy.events.persistence.factories.missions;

import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import com.daarthy.mini.shared.classes.yaml.AbstractMiniYaml;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionFactoryImpl extends AbstractMiniYaml implements MissionFactory {

   private SecureRandom random = new SecureRandom();

   public MissionFactoryImpl() {
       super("missions.yml");
   }

   /**
    * Este metodo a partir de un Grade S, A, B, C, D, E, F busca en la configuración uno al azar y devuelve la missión
    * , sin embargo es necesario no tener duplicados, con esto se le podría pasar una lista de strings y filtrar por ello
    *
    * */
   public HashMap<Mission, List<Objective>> getMission(Grade grade, boolean isDefaultGuild, Set<String> usedMissions) {
       HashMap<Mission, List<Objective>> mission = new HashMap<>();

       if (yamlFile.containsKey(grade.toString())) {
           List<Map<String, Object>> missions = yamlData.get(grade.toString());

           if (!missions.isEmpty()) {
               // Filtrar misiones ya usadas
               List<Map<String, Object>> availableMissions = new ArrayList<>();
               for (Map<String, Object> missionMap : missions) {
                   String missionTitle = (String) missionMap.get("title");
                   if (!usedMissions.contains(missionTitle)) {
                       availableMissions.add(missionMap);
                   }
               }

               // Si hay misiones disponibles, selecciona una aleatoria
               if (!availableMissions.isEmpty()) {
                   int randomMissionIndex = random.nextInt(availableMissions.size());
                   Map<String, Object> missionMap = availableMissions.get(randomMissionIndex);

                   // Procesar la misión seleccionada
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
                   usedMissions.add(missionData.getTitle());  // Añadir a la lista de misiones usadas
               }
           }
       }

       return mission;
   }

}
