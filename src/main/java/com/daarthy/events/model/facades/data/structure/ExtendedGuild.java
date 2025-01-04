package com.daarthy.events.model.facades.data.structure;

import com.daarthy.events.persistence.daos.guild.entities.Guild;

public interface ExtendedGuild {

    /**
     * Converts the current state of the guild into a persistent storage format.
     *
     * @return the {@link Guild} representing the current state of the guild.
     */
    Guild toStorageGuild();

    /**
     * Updates the level-up modifier for the guild and recalculates required experience.
     *
     * @param levelUpMod the new level-up modifier to apply.
     */
    void setLevelUpMod(Float levelUpMod);

    /**
     * Adds experience points to the guild and triggers level progression if applicable.
     *
     * @param addedExp the amount of experience points to add.
     */
    void addExperience(Float addedExp);

    /**
     * Removes experience points from the guild and triggers level reduction if applicable.
     *
     * @param removedExp the amount of experience points to remove.
     */
    void removeExperience(Float removedExp);

    /**
     * Retrieves the maximum job level attainable by the guild.
     *
     * @return the maximum job level as an integer.
     */
    int getMaxJobLevel();

    /**
     * Retrieves the required experience for the next guild level.
     *
     * @return the required experience.
     */
    Float getRequiredExp();
}
