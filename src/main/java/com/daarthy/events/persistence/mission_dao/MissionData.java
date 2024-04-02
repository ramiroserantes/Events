package com.daarthy.events.persistence.mission_dao;

import java.time.LocalDate;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MissionData {

    private Long missionId;
    private Long guildId;
    private String title;
    private Grade grade;
    private LocalDate expiration;
    private Integer maxCompletions;

    // References to current Players on this mission. NULL When retrieved by guild.
    private Integer currentPlayers;
    private Lock playerIncreaseLock = new ReentrantLock();

    public MissionData() {}

    public MissionData(Long missionId, Long guildId, String title, String grade, LocalDate expiration, Integer maxCompletions) {
        this.missionId = missionId;
        this.guildId = guildId;
        this.title = title;
        this.grade = Grade.valueOf(grade);
        this.expiration = expiration;
        this.maxCompletions = maxCompletions;
    }

    public MissionData(Long missionId, Long guildId, String title, String grade,
                       LocalDate expiration, Integer maxCompletions, Integer currentPlayers) {
        this.missionId = missionId;
        this.guildId = guildId;
        this.title = title;
        this.grade = Grade.valueOf(grade);
        this.expiration = expiration;
        this.maxCompletions = maxCompletions;
        this.currentPlayers = currentPlayers;
    }

    public Long getMissionId() {
        return missionId;
    }

    public Long getGuildId() {
        return guildId;
    }

    public String getTitle() {
        return title;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGrade(String grade) {
        this.grade = Grade.valueOf(grade);
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public void setMaxCompletions(Integer maxCompletions) {
        this.maxCompletions = maxCompletions;
    }

    public void setCurrentPlayers(Integer currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public Integer getMaxCompletions() {
        return maxCompletions;
    }

    public Integer getCurrentPlayers() {
        return currentPlayers;

    }

    public boolean addPlayer(int guildCapacity) {
        playerIncreaseLock.lock();
        if(maxCompletions == null) {
            currentPlayers++;
            playerIncreaseLock.unlock();
            return true;
        }

        if(currentPlayers >= (maxCompletions + guildCapacity)) {
            playerIncreaseLock.unlock();
            return false;
        } else {
            currentPlayers++;
            playerIncreaseLock.unlock();
            return true;
        }

    }

}
