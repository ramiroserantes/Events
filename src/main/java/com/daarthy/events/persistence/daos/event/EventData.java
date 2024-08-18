package com.daarthy.events.persistence.daos.event;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public String getRemainingTime() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime endDateTime = endDate.atStartOfDay();

        Duration duration = Duration.between(now, endDateTime);

        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();

        StringBuilder sb = new StringBuilder();
        sb.append(days).append(days == 1 ? " day" : " days");
        sb.append(", ").append(hours).append(" hour").append(hours == 1 ? "" : "s");
        sb.append(", ").append(minutes).append(" minute").append(minutes == 1 ? "" : "s");
        sb.append(".");

        return sb.toString();
    }
}
