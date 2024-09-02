package com.daarthy.events.app.modules.events.plans;

import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.util.List;

public class MiningEventPlan extends AbstractBasicEvents {

    public MiningEventPlan() {
        super(0.00002779, 0.0055246);
    }


    @Override
    protected void setUpActivities(List<ActionType> activities) {
        activities.add(ActionType.MINING);
        activities.add(ActionType.DIG);
    }


}
