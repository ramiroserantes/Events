package com.daarthy.events.persistence.daos.guild.entities;

import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Guild extends MiniEntity {

    @MiniId
    private Long id;
    @MiniDefaults(creationWith = "0")
    private Integer lvl;
    @MiniDefaults(creationWith = "0.0")
    private Float experience;
    @MiniDefaults(creationWith = "6")
    private Integer maxLvl;
    private String kName;
    @MiniDefaults(creationWith = "0")
    private Integer ampMissions;
    @MiniDefaults(creationWith = "0.0")
    private Float ampBasicRewards;
    private LocalDate lastTimeUpdated;
    @MiniDefaults(creationWith = "0.0")
    private Float levelUpMod;

    public Guild() {
    }

    private Guild(Builder builder) {
        this.id = builder.id;
        this.lvl = builder.lvl;
        this.experience = builder.experience;
        this.maxLvl = builder.maxLvl;
        this.kName = builder.kName;
        this.ampMissions = builder.ampMissions;
        this.ampBasicRewards = builder.ampBasicRewards;
        this.lastTimeUpdated = builder.lastTimeUpdated;
        this.levelUpMod = builder.levelUpMod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Float getExperience() {
        return experience;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public Integer getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(Integer maxLvl) {
        this.maxLvl = maxLvl;
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public Integer getAmpMissions() {
        return ampMissions;
    }

    public void setAmpMissions(Integer ampMissions) {
        this.ampMissions = ampMissions;
    }

    public Float getAmpBasicRewards() {
        return ampBasicRewards;
    }

    public void setAmpBasicRewards(Float ampBasicRewards) {
        this.ampBasicRewards = ampBasicRewards;
    }

    public LocalDate getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public void setLastTimeUpdated(LocalDate lastTimeUpdated) {
        this.lastTimeUpdated = lastTimeUpdated;
    }

    public Float getLevelUpMod() {
        return levelUpMod;
    }

    public void setLevelUpMod(Float levelUpMod) {
        this.levelUpMod = levelUpMod;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guild other = (Guild) o;
        return Objects.equals(isNew, other.isNew)
                && Objects.equals(id, other.id)
                && Objects.equals(lvl, other.lvl)
                && Objects.equals(experience, other.experience)
                && Objects.equals(maxLvl, other.maxLvl)
                && Objects.equals(kName, other.kName)
                && Objects.equals(ampMissions, other.ampMissions)
                && Objects.equals(ampBasicRewards, other.ampBasicRewards)
                && Objects.equals(lastTimeUpdated, other.lastTimeUpdated)
                && Objects.equals(levelUpMod, other.levelUpMod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lvl, experience, maxLvl, kName, ampMissions,
                ampBasicRewards, lastTimeUpdated, levelUpMod);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Integer lvl;
        private Float experience;
        private Integer maxLvl;
        private String kName;
        private Integer ampMissions;
        private Float ampBasicRewards;
        private LocalDate lastTimeUpdated;
        private Float levelUpMod;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder lvl(Integer lvl) {
            this.lvl = lvl;
            return this;
        }

        public Builder experience(Float experience) {
            this.experience = experience;
            return this;
        }

        public Builder maxLvl(Integer maxLvl) {
            this.maxLvl = maxLvl;
            return this;
        }

        public Builder kName(String kName) {
            this.kName = kName;
            return this;
        }

        public Builder ampMissions(Integer ampMissions) {
            this.ampMissions = ampMissions;
            return this;
        }

        public Builder ampBasicRewards(Float ampBasicRewards) {
            this.ampBasicRewards = ampBasicRewards;
            return this;
        }

        public Builder lastTimeUpdated(LocalDate lastTimeUpdated) {
            this.lastTimeUpdated = lastTimeUpdated;
            return this;
        }

        public Builder levelUpMod(Float levelUpMod) {
            this.levelUpMod = levelUpMod;
            return this;
        }

        public Guild build() {
            return new Guild(this);
        }
    }
}
