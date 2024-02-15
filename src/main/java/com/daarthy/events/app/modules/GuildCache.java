package com.daarthy.events.app.modules;

import java.time.LocalDateTime;

public class GuildCache {

    private String kName;
    private int ampMissions;
    private Float ampBasicRewards;
    private LocalDateTime lastTimeUpdated;
    private IGuildLevel guildLevel;
    private Float levelUpMod;

    public GuildCache(String kName, int ampMissions, Float ampBasicRewards, LocalDateTime lastTimeUpdated,
                      IGuildLevel guildLevel, Float levelUpMod) {
        this.kName = kName;
        this.ampMissions = ampMissions;
        this.ampBasicRewards = ampBasicRewards;
        this.lastTimeUpdated = lastTimeUpdated;
        this.guildLevel = guildLevel;
        this.levelUpMod = levelUpMod;
    }

    public IGuildLevel getGuildLevel() {
        return guildLevel;
    }

    public void setGuildLevel(IGuildLevel guildLevel) {
        this.guildLevel = guildLevel;
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
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

    public void setAmpBasicRewards(Float ampBasicRewards) {
        this.ampBasicRewards = ampBasicRewards;
    }

    public LocalDateTime getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(LocalDateTime lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Float getLevelUpMod() {
        return levelUpMod;
    }

    public void setLevelUpMod(Float levelUpMod) {
        this.levelUpMod = levelUpMod;
        guildLevel.computeRequiredExp(levelUpMod);
    }

    public int getMaxMissions() {
        return 4 + guildLevel.getCurrentLevel() + ampMissions;
    }

    public Float getAmplifiedProbability() {
        return (float) (guildLevel.getCurrentLevel() * 5);
    }
}
