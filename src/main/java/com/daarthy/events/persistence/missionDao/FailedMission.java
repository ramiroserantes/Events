package com.daarthy.events.persistence.missionDao;

import com.daarthy.events.app.modules.missions.Grade;

public class FailedMission {

    private Long missionId;
    private Grade grade;
    private int count;

    public FailedMission(Long missionId, Grade grade, int count) {
        this.missionId = missionId;
        this.grade = grade;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Long getMissionId() {
        return missionId;
    }

    public Grade getGrade() {
        return grade;
    }
}
