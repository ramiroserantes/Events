package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.annotations.ExclusionType;
import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniExclusion;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GuildMedals extends MiniEntity {

    @MiniExclusion(exclude = ExclusionType.TOTAL)
    private final Lock lock = new ReentrantLock();
    @MiniId
    private GuildMedalsKey guildMedalsKey;
    @MiniDefaults(creationWith = "0")
    private Integer medals;

    public GuildMedals() {
    }

    private GuildMedals(Builder builder) {
        this.guildMedalsKey = builder.guildMedalsKey;
        this.medals = builder.medals;
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public GuildMedalsKey getGuildMedalsKey() {
        return guildMedalsKey;
    }

    public void setGuildMedalsKey(GuildMedalsKey guildMedalsKey) {
        this.guildMedalsKey = guildMedalsKey;
    }

    public Integer getMedals() {
        return medals;
    }

    public void setMedals(Integer medals) {
        this.medals = medals;
    }

    // *****************************************************
    // Methods
    // *****************************************************
    public void addMedals() {
        lock.lock();
        this.medals += 1;
        lock.unlock();
    }

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

    public static class Builder {
        private GuildMedalsKey guildMedalsKey;
        private Integer medals;

        public Builder guildMedalsKey(GuildMedalsKey guildMedalsKey) {
            this.guildMedalsKey = guildMedalsKey;
            return this;
        }

        public Builder medals(Integer medals) {
            this.medals = medals;
            return this;
        }

        public GuildMedals build() {
            return new GuildMedals(this);
        }
    }
}
