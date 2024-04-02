package com.daarthy.events.persistence.player_dao;

public class PlayerData {

    private int maxMissions;
    private Float ampBasicRewards;
    private Long guildId;

    public PlayerData(int maxMissions, Float ampBasicRewards, Long guildId) {
        this.maxMissions = maxMissions;
        this.ampBasicRewards = ampBasicRewards;
        this.guildId = guildId;
    }

    public int getMaxMissions() {
        return maxMissions;
    }

    public void setMaxMissions(int maxMissions) {
        this.maxMissions = maxMissions;
    }

    public Float getAmpBasicRewards() {
        return ampBasicRewards;
    }

    public void setAmpBasicRewards(Float ampBasicRewards) {
        this.ampBasicRewards = ampBasicRewards;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }
}
