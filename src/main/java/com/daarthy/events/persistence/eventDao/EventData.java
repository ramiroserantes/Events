package com.daarthy.events.persistence.eventDao;

public class EventData {

    private Long eventId;
    private ScopeEnum scopeEnum;
    private String nameLink;
    private String descriptionLink;

    public EventData(Long eventId, ScopeEnum scopeEnum, String nameLink, String descriptionLink) {
        this.eventId = eventId;
        this.scopeEnum = scopeEnum;
        this.nameLink = nameLink;
        this.descriptionLink = descriptionLink;
    }


    public Long getEventId() {
        return eventId;
    }

    public ScopeEnum getScopeEnum() {
        return scopeEnum;
    }

    public String getNameLink() {
        return nameLink;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }
}
