package com.daarthy.events.persistence.daos.mission.entities;

import com.daarthy.mini.hibernate.parser.MiniString;
import com.daarthy.mini.shared.enums.festivals.Grade;

import java.util.Objects;

public class CompletionRate {

    private Grade grade;
    private Integer totalMissions;
    private Integer completedMissions;

    public Grade getGrade() {
        return grade;
    }

    public Integer getTotalMissions() {
        return totalMissions;
    }

    public Integer getCompletedMissions() {
        return completedMissions;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompletionRate other = (CompletionRate) o;
        return Objects.equals(grade, other.grade)
                && Objects.equals(totalMissions, other.totalMissions)
                && Objects.equals(completedMissions, other.completedMissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, totalMissions, completedMissions);
    }

    @Override
    public String toString() {
        return MiniString.toString(this);
    }
}
