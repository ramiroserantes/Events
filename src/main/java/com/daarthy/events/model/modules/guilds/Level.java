package com.daarthy.events.model.modules.guilds;

import java.util.concurrent.atomic.AtomicReference;

public class Level {

    private int currentLevel;
    private AtomicReference<Float> currentExp;
    private Float requiredExp;
    private int maxLevel;
    private Float levelUpMod;


    public Level(Float currentExp, int currentLevel, int maxLevel, Float levelUpMod) {
        this.currentExp = new AtomicReference<>(currentExp);
        this.currentLevel = currentLevel;
        this.maxLevel = maxLevel;
        this.levelUpMod = levelUpMod;
        computeRequiredExp();
    }

    public Float getLevelUpMod() {
        return levelUpMod;
    }

    public void setLevelUpMod(Float levelUpMod) {
        this.levelUpMod = levelUpMod;
        computeRequiredExp();
    }

    public void addExperience(Float addedExp) {
        if (currentLevel < maxLevel) {
            currentExp.updateAndGet(exp -> exp + addedExp);
            increaseLevel();
        }
    }

    public void removeExperience(Float removedExp) {
        currentExp.updateAndGet(exp -> exp - removedExp);
        decreaseLevel();
    }

    public void decreaseLevel() {
        if (currentExp.get() < 0 && currentLevel > 0) {
            currentLevel--;
            computeRequiredExp();
            currentExp.updateAndGet(exp -> requiredExp + exp);
        }
    }

    public void increaseLevel() {
        if (currentExp.get() >= requiredExp) {
            currentLevel++;
            if (currentLevel < maxLevel) {
                currentExp.updateAndGet(exp -> exp - requiredExp);
            } else {
                currentExp.set(0F);
            }
            computeRequiredExp();
        }
    }

    public void computeRequiredExp() {
        this.requiredExp = (50000 * (1 + currentLevel * 4)) * (1 - levelUpMod);
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

    public int getMaxJobLevel() {
        return 15 + currentLevel * 8;
    }
}
