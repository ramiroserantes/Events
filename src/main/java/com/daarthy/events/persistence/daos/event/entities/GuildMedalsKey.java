package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.hibernate.entities.MiniFragmentedKey;

import java.util.Objects;

public class GuildMedalsKey extends MiniFragmentedKey {

    private Long eventId;
    private Long guildId;

    public GuildMedalsKey() {}

    private GuildMedalsKey(Builder builder) {
        this.eventId = builder.eventId;
        this.guildId = builder.guildId;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getGuildId() {
        return guildId;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuildMedalsKey other = (GuildMedalsKey) o;
        return Objects.equals(eventId, other.eventId) &&
                Objects.equals(guildId, other.guildId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, guildId);
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
        private Long eventId;
        private Long guildId;

        public Builder eventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder guildId(Long guildId) {
            this.guildId = guildId;
            return this;
        }

        public GuildMedalsKey build() {
            return new GuildMedalsKey(this);
        }
    }
}

