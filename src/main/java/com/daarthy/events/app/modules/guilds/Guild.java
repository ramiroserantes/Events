package com.daarthy.events.app.modules.guilds;

import java.time.LocalDateTime;

public class Guild {

    private final String kName;
    private final LocalDateTime lastTimeUpdated;
    private final Level level;
    private final EventMedals eventMedals;
    private final GuildModifiers guildModifiers;

    private int watched = 1;

    public Guild(String kName, LocalDateTime lastTimeUpdated, Level level, EventMedals eventMedals, GuildModifiers guildModifiers) {
        this.kName = kName;
        this.lastTimeUpdated = lastTimeUpdated;
        this.level = level;
        this.eventMedals = eventMedals;
        this.guildModifiers = guildModifiers;
    }

    public String getkName() {
        return kName;
    }

    public LocalDateTime getLastTimeUpdated() {
        return lastTimeUpdated;
    }

    public Level getLevel() {
        return level;
    }

    public GuildModifiers getGuildModifiers() {
        return guildModifiers;
    }

    public EventMedals getEventMedals() {
        return eventMedals;
    }

    public int getMaxMissions() {
        return 4 + level.getCurrentLevel() + guildModifiers.getAmpMissions();
    }

    public Float getAmplifiedProbability() {
        return (float) (level.getCurrentLevel() * 5);
    }

    public synchronized void modifyWatch(int amount) {this.watched += amount;}

    public boolean isWatched() {
        return this.watched > 0;
    }
}
