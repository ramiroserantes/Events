package com.daarthy.events.model.modules.guilds;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EventMedals {

    private final ConcurrentMap<Long, Integer> medals = new ConcurrentHashMap<>();

    public void addMedals(Long eventId) {
        medals.merge(eventId, 1, Integer::sum);
    }

    public boolean removeMedals(Long eventId, int quantity) {
        return medals.computeIfPresent(eventId, (key, oldValue) -> {
            int newValue = oldValue - quantity;
            return newValue >= 0 ? newValue : null;
        }) != null;
    }

    public Integer getEventMedals(Long eventId) {
        return medals.getOrDefault(eventId, 0);
    }

    public ConcurrentMap<Long, Integer> getMedals() {
        return medals;
    }
}
