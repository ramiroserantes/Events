package com.daarthy.events.model.facades.mission.components.validations;

import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.shared.classes.auth.MiniAuth;

public interface PermissionChecker {

    /**
     * Validates if a mission can be accepted by a player.
     *
     * @param mission            The mission to check.
     * @param miniAuth           The authentication context of the player making the request.
     * @param guild              The guild to which the player belongs.
     * @param eventsPlayer       The player attempting to accept the mission.
     * @param persistenceContext The persistence context for accessing database operations.
     * @return A {@link StringBuilder} containing the error message if the mission cannot
     * be accepted, or an empty string if the mission is valid for acceptance.
     */
    StringBuilder checkMission(Mission mission, MiniAuth miniAuth, Guild guild, EventsPlayer eventsPlayer,
                               PersistenceContext persistenceContext);
}
