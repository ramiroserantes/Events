package com.daarthy.events.persistence.factories.rewards;

import com.daarthy.mini.shared.classes.economy.MiniReward;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;

public interface RewardsFactory {

    /**
     * Retrieves the reward configuration for the specified grade.
     *
     * @param grade the grade for which the reward is to be fetched.
     *              The grade typically represents the difficulty or level of the reward.
     * @return a {@link MiniReward} object containing the reward details
     *         (e.g., coins, experience points, food, minerals, leather).
     */
    MiniReward getReward(Grade grade);
}
