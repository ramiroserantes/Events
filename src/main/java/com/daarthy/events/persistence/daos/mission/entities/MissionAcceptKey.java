package com.daarthy.events.persistence.daos.mission.entities;

import com.daarthy.mini.hibernate.entities.MiniFragmentedKey;

import java.util.Objects;
import java.util.UUID;

public class MissionAcceptKey extends MiniFragmentedKey {

    private UUID playerId;
    private Long missionId;

    public MissionAcceptKey() {}

    private MissionAcceptKey(Builder builder) {
        this.playerId = builder.playerId;
        this.missionId = builder.missionId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Long getMissionId() {
        return missionId;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionAcceptKey other = (MissionAcceptKey) o;
        return Objects.equals(playerId, other.playerId) &&
                Objects.equals(missionId, other.missionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, missionId);
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
        private Long missionId;

        public Builder playerId(UUID playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder missionId(Long missionId) {
            this.missionId = missionId;
            return this;
        }

        public MissionAcceptKey build() {
            return new MissionAcceptKey(this);
        }
    }
}
