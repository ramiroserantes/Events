package com.daarthy.events.persistence.eventDao;

public enum ScopeEnum {

    ALL("All");

    private final String scope;

    ScopeEnum(String scope) {
        this.scope = scope;
    }

    public String getScopeEnumString() {
        return scope;
    }
}
