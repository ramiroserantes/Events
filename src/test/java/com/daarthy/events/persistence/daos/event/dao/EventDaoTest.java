package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.PersistenceTestContext;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.mini.shared.classes.enums.festivals.Scope;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventDaoTest {

    private final PersistenceTestContext ctx = new PersistenceTestContext();

    // *****************************************************
    // EventDao Selectors
    // *****************************************************

    @Test
    public void testEventSelectorsByCurrentEvents() {

        EventData eventData = ctx.getEvent();
        eventData.setMaxMedals(200);

        MiniCriteria<EventData> criteria = PostgresCriteria.<EventData>builder()
                .selector(FestivalSelector.FIND_CURRENT_EVENTS)
                .params(List.of(Scope.ALL))
                .resultClass(EventData.class)
                .build();

        List<EventData> foundEvents = ctx.searchDao().findByCriteria(criteria);

        assertFalse(foundEvents.isEmpty());
        assertTrue(foundEvents.contains(eventData));
    }

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }
}
