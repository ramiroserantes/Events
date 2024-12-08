package com.daarthy.events.persistence.daos.event.dao;

import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.mini.hibernate.jdbc.MiniSR;

public interface EventDao extends MiniSR<EventData, Long> {

}
