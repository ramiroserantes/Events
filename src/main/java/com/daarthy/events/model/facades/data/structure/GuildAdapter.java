package com.daarthy.events.model.facades.data.structure;

import com.daarthy.events.persistence.daos.guild.entities.Guild;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class GuildAdapter implements ExtendedGuild {
    private final Guild guild;
    private final AtomicInteger currentLevel;
    private final AtomicReference<Float> currentExp;
    private final ReentrantLock lock = new ReentrantLock();
    private volatile Float requiredExp;

    private GuildAdapter(Guild guild) {
        this.guild = guild;
        this.currentLevel = new AtomicInteger(guild.getLvl());
        this.currentExp = new AtomicReference<>(guild.getExperience());
        computeRequiredExp();
    }

    public static GuildAdapter create(Guild guild) {
        return (guild == null) ? null : new GuildAdapter(guild);
    }

    @Override
    public Guild toStorageGuild() {
        guild.setLvl(currentLevel.get());
        guild.setExperience(currentExp.get());
        return guild;
    }

    @Override
    public void setLevelUpMod(Float levelUpMod) {
        this.guild.setLevelUpMod(levelUpMod);
        computeRequiredExp();
    }

    @Override
    public void addExperience(Float addedExp) {
        lock.lock();
        try {
            currentExp.updateAndGet(exp -> exp + addedExp);
            increaseLevel();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeExperience(Float removedExp) {
        lock.lock();
        try {
            currentExp.updateAndGet(exp -> exp - removedExp);
            decreaseLevel();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getMaxJobLevel() {
        return 15 + currentLevel.get() * 8;
    }

    @Override
    public Float getRequiredExp() {
        return requiredExp;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    private void decreaseLevel() {
        while (currentExp.get() < 0 && currentLevel.get() > 0) {
            currentLevel.decrementAndGet();
            computeRequiredExp();
            currentExp.updateAndGet(exp -> exp + requiredExp);
        }
    }

    private void increaseLevel() {
        while (currentExp.get() >= requiredExp && currentLevel.get() < guild.getMaxLvl()) {
            currentLevel.incrementAndGet();
            currentExp.updateAndGet(exp -> exp - requiredExp);
            computeRequiredExp();
        }

        if (currentLevel.get() >= guild.getMaxLvl()) {
            currentExp.set(0F);
        }
    }

    private void computeRequiredExp() {
        this.requiredExp = (50000 * (1 + currentLevel.get() * 4)) * (1 - guild.getLevelUpMod());
    }
}
