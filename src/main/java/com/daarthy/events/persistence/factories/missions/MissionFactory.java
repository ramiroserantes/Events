package com.daarthy.events.persistence.factories.missions;

import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MissionFactory {

    /**
     * Retrieves a mission along with its associated objectives based on the given grade and other parameters.
     * The mission is selected randomly from a predefined set of missions configured in the system.
     *
     * @param grade          the grade or difficulty level of the mission (e.g., S, A, B, C, D, E, F).
     *                       This determines the mission's complexity and priority.
     * @param isDefaultGuild a flag indicating whether the mission belongs to a default guild. If true,
     *                       certain properties of the mission, such as maximum completions, may differ.
     * @param usedMissions   a set of titles representing missions that have already been used. This ensures
     *                       that the returned mission is unique and not duplicated.
     * @return a map where the key is a {@link Mission} object representing the mission details, and the value
     * is a list of {@link Objective} objects associated with the mission. If no mission is available,
     * the map will be empty.
     */
    Map<Mission, List<Objective>> getMission(Grade grade, boolean isDefaultGuild, Set<String> usedMissions);
}
