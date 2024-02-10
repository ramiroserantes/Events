package com.daarthy.events.persistence.missionDao;

public enum MissionStatus {

    ACCEPTED("Accepted"),
    FINALIZED("Finalized"),
    FAILED("Failed");

    private final String status;

    MissionStatus(String status) {
        this.status = status;
    }

    public String getStatusString() {
        return status;
    }

}
