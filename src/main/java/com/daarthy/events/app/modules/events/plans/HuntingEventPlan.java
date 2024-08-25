package com.daarthy.events.app.modules.events.plans;

import com.daarthy.mini.shared.enums.festivals.ActionType;

import java.util.List;

public class HuntingEventPlan extends AbstractBasicEvents {

    public HuntingEventPlan() {
        super(0.0011904,0.0042328);
    }


    @Override
    protected void setUpActivities(List<ActionType> activities) {
        activities.add(ActionType.KILL);
    }
}


