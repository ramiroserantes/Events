package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.hibernate.entities.MiniFragmentedKey;

import java.util.Objects;
import java.util.UUID;

public class ContributionKey extends MiniFragmentedKey {

    private UUID playerId;

    private Long eventId;

    public ContributionKey() {}

    private ContributionKey(Builder builder) {
        this.playerId = builder.playerId;
        this.eventId = builder.eventId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Long getEventId() {
        return eventId;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContributionKey other = (ContributionKey) o;
        return Objects.equals(playerId, other.playerId) &&
                Objects.equals(eventId, other.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, eventId);
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
        private Long eventId;

        public Builder playerId(UUID playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder eventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public ContributionKey build() {
            return new ContributionKey(this);
        }
    }
}
