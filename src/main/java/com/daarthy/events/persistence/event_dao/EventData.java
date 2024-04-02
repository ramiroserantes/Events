package com.daarthy.events.persistence.event_dao;

import java.time.LocalDate;

public class EventData {

    private Long eventId;
    private ScopeEnum scopeEnum;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxMedals;

    public EventData(ScopeEnum scopeEnum, String name, LocalDate startDate, LocalDate endDate, int maxMedals) {
        this.scopeEnum = scopeEnum;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMedals = maxMedals;
    }

    public EventData(Long eventId, ScopeEnum scopeEnum, String name, LocalDate startDate, LocalDate endDate, int maxMedals) {
        this.eventId = eventId;
        this.scopeEnum = scopeEnum;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMedals = maxMedals;
    }

    public Long getEventId() {
        return eventId;
    }

    public ScopeEnum getScopeEnum() {
        return scopeEnum;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getMaxMedals() {
        return maxMedals;
    }
}
