package com.daarthy.events.persistence.daos.mission;

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

    private final String actionTypeName;

    ActionType(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public String getActionTypeString() {
        return actionTypeName;
    }
}
