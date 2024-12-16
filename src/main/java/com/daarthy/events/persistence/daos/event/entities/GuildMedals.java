package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;

public class GuildMedals extends MiniEntity {

    @MiniId
    private GuildMedalsKey guildMedalsKey;
    @MiniDefaults(creationWith = "0")
    private int medals;

    public GuildMedals() {}

    private GuildMedals(Builder builder) {
        this.guildMedalsKey = builder.guildMedalsKey;
        this.medals = builder.medals;
    }

    public GuildMedalsKey getGuildMedalsKey() {
        return guildMedalsKey;
    }

    public void setGuildMedalsKey(GuildMedalsKey guildMedalsKey) {
        this.guildMedalsKey = guildMedalsKey;
    }

    public int getMedals() {
        return medals;
    }

    public void setMedals(int medals) {
        this.medals = medals;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuildMedals other = (GuildMedals) o;
        return Objects.equals(guildMedalsKey, other.guildMedalsKey) &&
                Objects.equals(medals, other.medals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildMedalsKey, medals);
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

        private GuildMedalsKey guildMedalsKey;
        private int medals;

        public Builder guildMedalsKey(GuildMedalsKey guildMedalsKey) {
            this.guildMedalsKey = guildMedalsKey;
            return this;
        }

        public Builder medals(int medals) {
            this.medals = medals;
            return this;
        }

        public GuildMedals build() {
            return new GuildMedals(this);
        }
    }
}
