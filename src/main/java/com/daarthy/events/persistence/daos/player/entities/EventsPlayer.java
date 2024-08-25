package com.daarthy.events.persistence.daos.player.entities;

import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.annotations.MiniTable;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;
import java.util.UUID;

@MiniTable(table = "Player")
public class EventsPlayer extends MiniEntity {

    @MiniId
    private UUID playerId;
    private int maxMissions;
    private Float ampBasicRewards;
    private Long guildId;

    public EventsPlayer() {
    }

    public EventsPlayer(Builder builder) {
        this.playerId = builder.playerId;
        this.maxMissions = builder.maxMissions;
        this.ampBasicRewards = builder.ampBasicRewards;
        this.guildId = builder.guildId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
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

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventsPlayer other = (EventsPlayer) o;
        return isNew == other.isNew &&
                maxMissions == other.maxMissions
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

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID playerId;
        private int maxMissions;
        private Float ampBasicRewards;
        private Long guildId;

        public Builder playerId(UUID playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder maxMissions(int maxMissions) {
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
