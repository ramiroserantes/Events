package com.daarthy.events.app.modules.guilds;

import com.daarthy.events.persistence.mission_dao.Grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Penalty {

    private Map<Long, List<Grade>> penalties = new HashMap<>();

    public void addPenalty(Long guildId, Grade grade) {

        List<Grade> grades = penalties.getOrDefault(guildId, null);

        if(grades == null) {
            grades = new ArrayList<>();
        }

        grades.add(grade);

    }

    public Map<Long, List<Grade>> getPenalties() {
        return penalties;
    }
}
