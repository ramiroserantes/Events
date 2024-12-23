package com.daarthy.events.model.modules.events;

public enum EventToken {

    HUNTING_EVENT,
    MINING_EVENT,
    GATHERING_EVENT;

    public static EventToken fromString(String eventName) {
        for (EventToken eventToken : EventToken.values()) {
            if (eventToken.name().replace("_", "").equalsIgnoreCase(eventName)) {
                return eventToken;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + eventName);
    }
}
