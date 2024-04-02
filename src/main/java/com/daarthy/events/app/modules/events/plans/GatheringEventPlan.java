package com.daarthy.events.app.modules.events.plans;

import com.daarthy.events.persistence.mission_dao.ActionType;
import java.util.*;

public class GatheringEventPlan extends AbstractBasicEvents {

    public GatheringEventPlan() {
        super(0.00008337,0.0054629);
    }

    @Override
    protected void setUpActivities(List<ActionType> activities) {
        activities.add(ActionType.FARM);
        activities.add(ActionType.CUT);
    }
}
