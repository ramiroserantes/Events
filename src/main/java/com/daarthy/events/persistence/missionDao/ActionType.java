package com.daarthy.events.persistence.missionDao;

public enum ActionType {
    MINING("Mining"),
    DIG("Dig"),
    FARM("Farm"),
    CUT("Cut"),
    KILL("Kill"),
    BREAK("Break"),
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
