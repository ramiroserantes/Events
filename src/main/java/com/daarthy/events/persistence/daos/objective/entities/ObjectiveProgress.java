package com.daarthy.events.persistence.daos.objective.entities;

import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;

public class ObjectiveProgress extends MiniEntity {

    @MiniId
    private ObjectiveProgressKey key;
    private int amount;

    public ObjectiveProgress() {}

    public ObjectiveProgress(Builder builder) {}

    public ObjectiveProgressKey getKey() {
        return key;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveProgress other = (ObjectiveProgress) o;
        return Objects.equals(key, other.key) &&
                amount == other.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, amount);
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
        private ObjectiveProgressKey key;
        private int amount;

        public Builder key(ObjectiveProgressKey key) {
            this.key = key;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public ObjectiveProgress build() {
            return new ObjectiveProgress(this);
        }
    }

}
