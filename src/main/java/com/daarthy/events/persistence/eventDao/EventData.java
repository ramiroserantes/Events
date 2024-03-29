package com.daarthy.events.persistence.eventDao;

import java.util.Date;

public class EventData {

    private Long eventId;
    private ScopeEnum scopeEnum;
    private String name;
    private Date startDate;
    private Date endDate;
    private int maxMedals;

    public EventData(ScopeEnum scopeEnum, String name, Date startDate, Date endDate, int maxMedals) {
        this.scopeEnum = scopeEnum;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxMedals = maxMedals;
    }

    public EventData(Long eventId, ScopeEnum scopeEnum, String name, Date startDate, Date endDate, int maxMedals) {
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
