package com.daarthy.events.persistence.missionDao;

import java.time.LocalDateTime;

public class MissionData {

    private Long missionId;
    private Long guildId;
    private String title;
    private String grade;
    private LocalDateTime expiration;
    private Integer maxCompletions;

    // References to current Players on this mission. NULL When retrieved by guild.
    private Integer currentPlayers;

    public MissionData(Long missionId, Long guildId, String title, String grade, LocalDateTime expiration, Integer maxCompletions) {
        this.missionId = missionId;
        this.guildId = guildId;
        this.title = title;
        this.grade = grade;
        this.expiration = expiration;
        this.maxCompletions = maxCompletions;
    }

    public MissionData(Long missionId, Long guildId, String title, String grade,
                       LocalDateTime expiration, Integer maxCompletions, Integer currentPlayers) {
        this.missionId = missionId;
        this.guildId = guildId;
        this.title = title;
        this.grade = grade;
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

    public String getGrade() {
        return grade;
    }

    public LocalDateTime getExpiration() {
        return expiration;
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
        this.grade = grade;
    }

    public void setExpiration(LocalDateTime expiration) {
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
}
