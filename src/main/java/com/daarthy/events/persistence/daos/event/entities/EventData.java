package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.annotations.MiniTable;
import com.daarthy.mini.hibernate.entities.MiniEntity;
import com.daarthy.mini.shared.classes.enums.festivals.Scope;

import java.time.LocalDate;
import java.util.Objects;

@MiniTable(table = "Events")
public class EventData extends MiniEntity {

    @MiniId
    private Long eventId;
    private Scope worldScope;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxMedals;

    public EventData() {
    }

    private EventData(Builder builder) {
        this.eventId = builder.eventId;
        this.worldScope = builder.worldScope;
        this.name = builder.name;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.maxMedals = builder.maxMedals;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Scope getWorldScope() {
        return worldScope;
    }

    public void setWorldScope(Scope worldScope) {
        this.worldScope = worldScope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxMedals() {
        return maxMedals;
    }

    public void setMaxMedals(Integer maxMedals) {
        this.maxMedals = maxMedals;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventData other = (EventData) o;
        return maxMedals == other.maxMedals
                && Objects.equals(eventId, other.eventId)
                && worldScope == other.worldScope
                && Objects.equals(name, other.name)
                && Objects.equals(startDate, other.startDate)
                && Objects.equals(endDate, other.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, worldScope, name, startDate, endDate, maxMedals);
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long eventId;
        private Scope worldScope;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer maxMedals;

        public Builder eventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder scopeEnum(Scope scopeEnum) {
            this.worldScope = scopeEnum;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder maxMedals(Integer maxMedals) {
            this.maxMedals = maxMedals;
            return this;
        }

        public EventData build() {
            return new EventData(this);
        }
    }
}
