package com.daarthy.events.model.facades.mission.components;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

public interface MissionLgnComp {

    void loginPlayer(EventsPlayer player);

    void logoutPlayer(EventsPlayer player);
}
