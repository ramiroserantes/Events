package com.daarthy.events.app.modules.missions;

import com.daarthy.events.persistence.daos.mission.Grade;

import java.time.LocalDate;

public class Objective {

    private final Long missionId;
    private LocalDate expirationDate;

    private final Grade grade;
    private final String target;
    private final Integer level;
    private final int reqAmount;

    private int observers = 1;

    public Objective(Long missionId, LocalDate expirationDate, Grade grade, String target, Integer level, int reqAmount) {
        this.missionId = missionId;
        this.expirationDate = expirationDate;
        this.grade = grade;
        this.target = target;
        this.level = level;
        this.reqAmount = reqAmount;
    }

    public Long getMissionId() {
        return missionId;
    }

    public String getTarget() {
        return target;
    }

    public Integer getLevel() {
        return level;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public Grade getGrade() {
        return grade;
    }


    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public synchronized void updateObserved(int amount) {
        this.observers += amount;
    }

    public boolean isObserved() {
        return observers > 0;
    }

    public boolean match(String target, Integer level) {

        return target.equalsIgnoreCase(this.target) && (this.level == null ||
                (level != null && level >= this.level));
    }
}
