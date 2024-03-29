package com.daarthy.events.app.modules.guilds;

import java.util.concurrent.atomic.AtomicReference;

public class GuildLevel implements IGuildLevel {

    private int currentLevel;
    private AtomicReference<Float> currentExp;
    private Float requiredExp;
    private int maxLevel;

    public GuildLevel(Float currentExp, int currentLevel, int maxLevel, Float levelUpMod) {
        this.currentExp = new AtomicReference<>(currentExp);
        this.currentLevel = currentLevel;
        this.maxLevel = maxLevel;
        this.requiredExp = computeRequiredExp(levelUpMod);
    }

    public void addExperience(Float addedExp, Float levelUpMod) {
        currentExp.updateAndGet(exp -> exp + addedExp);
        increaseLevel(levelUpMod);
    }

    public void removeExperience(Float removedExp, Float levelUpMod) {
        currentExp.updateAndGet(exp -> exp - removedExp);
        decreaseLevel(levelUpMod);
    }

    public void decreaseLevel(Float levelUpMod) {
        if (currentExp.get() < 0 && currentLevel > 0) {
            currentLevel--;
            requiredExp = computeRequiredExp(levelUpMod);
            currentExp.updateAndGet(exp -> requiredExp + exp);
        }
    }

    public void increaseLevel(Float levelUpMod) {
        if (currentExp.get() >= requiredExp) {
            currentLevel++;
            currentExp.updateAndGet(exp -> requiredExp - exp);
            requiredExp = computeRequiredExp(levelUpMod);
        }
    }

    public Float computeRequiredExp(Float levelUpMod) {
        return (50000 * (1 + currentLevel * 4)) * (1 - levelUpMod);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public Float getCurrentExp() {
        return currentExp.get();
    }

    public Float getRequiredExp() {
        return requiredExp;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
