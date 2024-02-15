package com.daarthy.events.app.modules;

public interface IGuildLevel {

    void addExperience(Float addedExp, Float levelUpMod);

    void removeExperience(Float removedExp, Float levelUpMod);

    int getCurrentLevel();

    Float getCurrentExp();

    Float getRequiredExp();

    int getMaxLevel();

    void setMaxLevel(int maxLevel);

    Float computeRequiredExp(Float levelUpMod);

}
