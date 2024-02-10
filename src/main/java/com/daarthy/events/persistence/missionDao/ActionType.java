package com.daarthy.events.persistence.missionDao;

public enum ActionType {
    BREAK("Break"),
    KILL("Kill"),
    PLACE("Place"),
    CRAFT("Craft"),
    MAKE("Make"),
    FISH("Fish");

    private final String actionType;

    ActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionTypeString() {
        return actionType;
    }
}
