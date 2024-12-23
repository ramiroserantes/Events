package com.daarthy.events.persistence.daos.mission.entities;

import com.daarthy.mini.annotations.ExclusionType;
import com.daarthy.mini.annotations.MiniExclusion;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.annotations.MiniTable;
import com.daarthy.mini.hibernate.entities.MiniEntity;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@MiniTable(table = "Missions")
public class Mission extends MiniEntity {

    @MiniId(generated = true)
    private Long id;
    private Long guildId;
    private String title;
    private Grade grade;
    private LocalDate expiration;
    private Integer maxCompletions;

    @MiniExclusion(exclude = ExclusionType.PARTIAL)
    private Integer currentPlayers;

    @MiniExclusion(exclude = ExclusionType.TOTAL)
    private Lock playerIncreaseLock = new ReentrantLock();

    public Mission() {
    }

    private Mission(Builder builder) {
        this.id = builder.id;
        this.guildId = builder.guildId;
        this.title = builder.title;
        this.grade = builder.grade;
        this.expiration = builder.expiration;
        this.maxCompletions = builder.maxCompletions;
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public Integer getMaxCompletions() {
        return maxCompletions;
    }

    public void setMaxCompletions(Integer maxCompletions) {
        this.maxCompletions = maxCompletions;
    }

    public Integer getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(Integer currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission other = (Mission) o;
        return Objects.equals(id, other.id)
                && Objects.equals(guildId, other.guildId)
                && Objects.equals(title, other.title)
                && Objects.equals(grade, other.grade)
                && Objects.equals(expiration, other.expiration)
                && Objects.equals(maxCompletions, other.maxCompletions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guildId, title, grade, expiration,
                maxCompletions);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    public static class Builder {
        private Long id;
        private Long guildId;
        private String title;
        private Grade grade;
        private LocalDate expiration;
        private Integer maxCompletions;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder guildId(Long guildId) {
            this.guildId = guildId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder grade(Grade grade) {
            this.grade = grade;
            return this;
        }

        public Builder expiration(LocalDate expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder maxCompletions(Integer maxCompletions) {
            this.maxCompletions = maxCompletions;
            return this;
        }

        public Mission build() {
            return new Mission(this);
        }
    }
}
