package com.daarthy.events.persistence.daos.objective.entities;

import com.daarthy.mini.hibernate.entities.MiniFragmentedKey;

import java.util.Objects;
import java.util.UUID;

public class ObjectiveProgressKey extends MiniFragmentedKey {

    private UUID playerId;
    private Long objectiveId;

    public ObjectiveProgressKey() {
    }

    private ObjectiveProgressKey(Builder builder) {
        this.playerId = builder.playerId;
        this.objectiveId = builder.objectiveId;
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

    public Long getObjectiveId() {
        return objectiveId;
    }

    // *****************************************************
    // Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveProgressKey other = (ObjectiveProgressKey) o;
        return Objects.equals(playerId, other.playerId) &&
                Objects.equals(objectiveId, other.objectiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, objectiveId);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    public static class Builder {
        private UUID playerId;
        private Long objectiveId;

        public Builder playerId(UUID playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder objectiveId(Long objectiveId) {
            this.objectiveId = objectiveId;
            return this;
        }

        public ObjectiveProgressKey build() {
            return new ObjectiveProgressKey(this);
        }
    }
}
