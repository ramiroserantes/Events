package com.daarthy.events.model.facades.event.components;

import com.daarthy.events.Events;
import com.daarthy.events.model.facades.event.memory.EventMemory;
import com.daarthy.events.model.facades.event.structure.ExtendedEvent;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.event.entities.ContributionKey;
import com.daarthy.events.persistence.daos.event.entities.GuildMedals;
import com.daarthy.events.persistence.daos.event.entities.GuildMedalsKey;
import com.daarthy.events.persistence.daos.event.entities.PlayerContribution;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;

public class EventLgnCompImpl extends EventAbstractComp implements EventLgnComp {

    public EventLgnCompImpl(EventMemory memory, PersistenceContext persistenceContext) {
        super(memory, persistenceContext);
    }

    @Override
    public void loginPlayer(EventsPlayer player) {

        for (ExtendedEvent e : memory.getEvents()) {
            PlayerContribution contribution = persistenceContext.playerContributionDao().findById(
                    ContributionKey.builder()
                            .eventId(e.getEventData().getId())
                            .playerId(player.getPlayerId())
                            .build()
            );

            if (contribution != null) {
                e.addPlayer(player, contribution);
            } else {
                e.addPlayer(player, persistenceContext.playerContributionDao().save(PlayerContribution.builder()
                        .contributionKey(ContributionKey.builder()
                                .eventId(e.getEventData().getId())
                                .playerId(player.getPlayerId())
                                .build())
                        .medals(0)
                        .items(0)
                        .build()));
            }

            GuildMedalsKey searchKey = GuildMedalsKey.builder()
                    .guildId(player.getGuildId())
                    .eventId(e.getEventData().getId())
                    .build();

            if (!player.getGuildId().equals(Events.BASIC_GUILD)) {
                memory.getGuildMedals().computeIfAbsent(searchKey, key -> {
                    GuildMedals newGuildMedals = GuildMedals.builder()
                            .guildMedalsKey(key)
                            .build();
                    return persistenceContext.guildMedalsDao().save(newGuildMedals);
                });
            }
        }

    }

    @Override
    public void logoutPlayer(EventsPlayer player) {
        Long candidateGuild = player.getGuildId();
        memory.getEvents().forEach(event -> {
            if (event.getPlayers().containsKey(player)) {
                persistenceContext.playerContributionDao().save(event.getPlayers().get(player));
                event.getPlayers().remove(player);
            }
        });

        boolean hasOtherPlayersInGuild = memory.getEvents().stream().anyMatch(
                event -> event.getPlayers().entrySet().stream().anyMatch(
                        p -> p.getKey().getGuildId().equals(candidateGuild) && !p.equals(player)));

        if (!hasOtherPlayersInGuild) {
            memory.getGuildMedals().entrySet().removeIf(entry ->
                    entry.getKey().getGuildId().equals(candidateGuild));
        }

    }
}
