package com.daarthy.events.persistence.factories.rewards;

import com.daarthy.events.Events;
import com.daarthy.events.persistence.factories.messages.languages.AbstractMessage;
import com.daarthy.mini.shared.classes.economy.MiniReward;
import com.daarthy.mini.shared.classes.enums.festivals.Grade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RewardsFactoryImpl extends AbstractMessage implements RewardsFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsFactoryImpl.class);
    private static final String FILE = "configuration/rewards.yml";
    private static final String COINS_ATTRIBUTE = "coins";
    private static final String EXP_ATTRIBUTE = "exp";
    private static final String FOOD_ATTRIBUTE = "food";
    private static final String MINERALS_ATTRIBUTE = "minerals";
    private static final String LEATHER_ATTRIBUTE = "leather";

    public RewardsFactoryImpl() {
        super(FILE);
    }

    @Override
    public MiniReward getReward(Grade grade) {
        return processRewards(getRewardsRoot(grade));
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @SuppressWarnings("unchecked")
    private Map<String, Object> getRewardsRoot(Grade grade) {
        if (yamlFile.containsKey(grade.toString())) {
            return (Map<String, Object>) yamlFile.get(grade.toString());
        } else {
            LOGGER.error(Events.MICRO_NAME + "- Error in retrieving the rewards grade {}",
                    grade.getValue());
            return new HashMap<>();
        }
    }

    private MiniReward processRewards(Map<String, Object> data) {
        return MiniReward.builder()
                .coins(Float.valueOf(data.get(COINS_ATTRIBUTE).toString()))
                .exp(Float.valueOf(data.get(EXP_ATTRIBUTE).toString()))
                .food(Integer.valueOf(data.get(FOOD_ATTRIBUTE).toString()))
                .minerals(Integer.valueOf(data.get(MINERALS_ATTRIBUTE).toString()))
                .leather(Integer.valueOf(data.get(LEATHER_ATTRIBUTE).toString()))
                .build();
    }
}
