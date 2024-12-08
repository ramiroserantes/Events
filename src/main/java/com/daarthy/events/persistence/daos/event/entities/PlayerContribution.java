package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;

public class PlayerContribution extends MiniEntity {

    @MiniId
    private ContributionKey contributionKey;
    private int items = 0;
    private int medals = 0;

    public PlayerContribution() {
    }

    private PlayerContribution(Builder builder) {
        this.contributionKey = builder.contributionKey;
        this.items = builder.items;
        this.medals = builder.medals;
    }

    public ContributionKey getContributionKey() {
        return contributionKey;
    }

    public void setContributionKey(ContributionKey contributionKey) {
        this.contributionKey = contributionKey;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public int getMedals() {
        return medals;
    }

    public void setMedals(int medals) {
        this.medals = medals;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerContribution other = (PlayerContribution) o;
        return items == other.items
                && medals == other.medals
                && Objects.equals(contributionKey, other.contributionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contributionKey, items, medals);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    // *****************************************************
    // Builder Pattern
    // *****************************************************
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ContributionKey contributionKey;
        private int items;
        private int medals;

        public Builder contributionKey(ContributionKey contributionKey) {
            this.contributionKey = contributionKey;
            return this;
        }

        public Builder items(int items) {
            this.items = items;
            return this;
        }

        public Builder medals(int medals) {
            this.medals = medals;
            return this;
        }

        public PlayerContribution build() {
            return new PlayerContribution(this);
        }
    }
}
