package com.daarthy.events.persistence.guildDao;

import java.time.LocalDateTime;

public class GuildData {

    private Long guildId;
    private String kName;
    private int lvl;
    private Float experience;
    private int maxLvL;
    private int ampMissions;
    private Float ampBasicRewards;
    private LocalDateTime lastTimeUpdated;
    private Float levelUpMod;

    public GuildData(Long guildId, String kName, int lvl, Float experience, int maxLvL,
                     int ampMissions, Float ampBasicRewards, float levelUpMod, LocalDateTime lastTimeUpdated) {
        this.guildId = guildId;
        this.kName = kName;
        this.lvl = lvl;
        this.experience = experience;
        this.maxLvL = maxLvL;
        this.ampMissions = ampMissions;
        this.ampBasicRewards = ampBasicRewards;
        this.levelUpMod = levelUpMod;
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Float getLevelUpMod() {
        return levelUpMod;
    }

    public void setLevelUpMod(Float levelUpMod) {
        this.levelUpMod = levelUpMod;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public Float getExperience() {
        return experience;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public int getMaxLvL() {
        return maxLvL;
    }

    public void setMaxLvL(int maxLvL) {
        this.maxLvL = maxLvL;
    }

    public int getAmpMissions() {
        return ampMissions;
    }

    public void setAmpMissions(int ampMissions) {
        this.ampMissions = ampMissions;
    }

    public Float getAmpBasicRewards() {
        return ampBasicRewards;
    }

    public LocalDateTime getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(LocalDateTime lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public void setAmpBasicRewards(Float ampBasicRewards) {
        this.ampBasicRewards = ampBasicRewards;
    }


}
