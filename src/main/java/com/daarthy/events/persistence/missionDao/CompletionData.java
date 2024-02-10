package com.daarthy.events.persistence.missionDao;

public class CompletionData {

    private int totalAccepted;
    private int finalized;

    public CompletionData(int totalAccepted, int finalized) {
        this.totalAccepted = totalAccepted;
        this.finalized = finalized;
    }

    public Float getPercentage() {
        return ((float)finalized / (float)totalAccepted) * 100;
    }

    public int getTotalAccepted() {
        return totalAccepted;
    }

    public int getFinalized() {
        return finalized;
    }
}
