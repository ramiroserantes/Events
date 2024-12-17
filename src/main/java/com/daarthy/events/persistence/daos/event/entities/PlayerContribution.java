package com.daarthy.events.persistence.daos.event.entities;

import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;

public class PlayerContribution extends MiniEntity {

    @MiniId
    private ContributionKey contributionKey;
    @MiniDefaults(creationWith = "0")
    private Integer items;
    @MiniDefaults(creationWith = "0")
    private Integer medals;

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

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Integer getMedals() {
        return medals;
    }

    public void setMedals(Integer medals) {
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
        return Objects.equals(items, other.items)
                && Objects.equals(medals, other.medals)
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
        private Integer items;
        private Integer medals;

        public Builder contributionKey(ContributionKey contributionKey) {
            this.contributionKey = contributionKey;
            return this;
        }

        public Builder items(Integer items) {
            this.items = items;
            return this;
        }

        public Builder medals(Integer medals) {
            this.medals = medals;
            return this;
        }

        public PlayerContribution build() {
            return new PlayerContribution(this);
        }
    }
}
