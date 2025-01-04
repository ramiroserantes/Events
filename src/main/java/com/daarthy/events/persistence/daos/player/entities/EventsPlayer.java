package com.daarthy.events.persistence.daos.player.entities;

import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.annotations.MiniTable;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;
import java.util.UUID;

@MiniTable(table = "Player")
public class EventsPlayer extends MiniEntity {

    @MiniId
    private UUID playerId;
    @MiniDefaults(creationWith = "3")
    private Integer maxMissions;
    @MiniDefaults(creationWith = "0.0")
    private Float ampBasicRewards;
    @MiniDefaults(creationWith = "1")
    private Long guildId;

    public EventsPlayer() {
    }

    private EventsPlayer(Builder builder) {
        this.playerId = builder.playerId;
        this.maxMissions = builder.maxMissions;
        this.ampBasicRewards = builder.ampBasicRewards;
        this.guildId = builder.guildId;
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Integer getMaxMissions() {
        return maxMissions;
    }

    public void setMaxMissions(Integer maxMissions) {
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

    // *****************************************************
    // Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventsPlayer other = (EventsPlayer) o;
        return Objects.equals(maxMissions, other.maxMissions)
                && Objects.equals(playerId, other.playerId)
                && Objects.equals(ampBasicRewards, other.ampBasicRewards)
                && Objects.equals(guildId, other.guildId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, maxMissions, ampBasicRewards, guildId, isNew);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    public static class Builder {
        private UUID playerId;
        private Integer maxMissions;
        private Float ampBasicRewards;
        private Long guildId;

        public Builder playerId(UUID playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder maxMissions(Integer maxMissions) {
            this.maxMissions = maxMissions;
            return this;
        }

        public Builder ampBasicRewards(Float ampBasicRewards) {
            this.ampBasicRewards = ampBasicRewards;
            return this;
        }

        public Builder guildId(Long guildId) {
            this.guildId = guildId;
            return this;
        }

        public EventsPlayer build() {
            return new EventsPlayer(this);
        }
    }

}
