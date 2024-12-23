package com.daarthy.events.model.modules.guilds;

public class GuildModifiers {

    private int ampMissions;
    private Float ampBasicRewards;

    public GuildModifiers(int ampMissions, Float ampBasicRewards) {
        this.ampMissions = ampMissions;
        this.ampBasicRewards = ampBasicRewards;
    }

    public int getAmpMissions() {
        return ampMissions;
    }

    public void setAmpMissions(int ampMissions) {
        this.ampMissions = ampMissions;
    }

    public Float getAmpBasicRewards() {
        return ampBasicRewards;
    }

    public void setAmpBasicRewards(Float ampBasicRewards) {
        this.ampBasicRewards = ampBasicRewards;
    }

}
