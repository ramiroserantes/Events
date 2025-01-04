package com.daarthy.events.model.facades.event.structure.plans;

import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.util.List;

public class GatheringEventPlan extends AbstractBasicEvents {

    public GatheringEventPlan() {
        super(0.00008337, 0.0054629);
    }

    @Override
    protected void setUpActivities(List<ActionType> activities) {
        activities.add(ActionType.FARM);
        activities.add(ActionType.CUT);
    }

}
