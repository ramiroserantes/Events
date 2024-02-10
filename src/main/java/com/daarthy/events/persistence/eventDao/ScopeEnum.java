package com.daarthy.events.persistence.eventDao;

public enum ScopeEnum {

    ALL("All"),
    CAVE_WORLD("CaveWorld");

    private final String scope;

    ScopeEnum(String scope) {
        this.scope = scope;
    }

    public String getScopeEnumString() {
        return scope;
    }
}
