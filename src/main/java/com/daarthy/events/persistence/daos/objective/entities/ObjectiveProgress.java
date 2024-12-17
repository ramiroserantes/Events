package com.daarthy.events.persistence.daos.objective.entities;

import com.daarthy.mini.annotations.MiniDefaults;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;

import java.util.Objects;

public class ObjectiveProgress extends MiniEntity {

    @MiniId
    private ObjectiveProgressKey key;
    @MiniDefaults(creationWith = "0")
    private Integer amount;

    public ObjectiveProgress() {}

    private ObjectiveProgress(Builder builder) {
        this.key = builder.key;
        this.amount = builder().amount;
    }

    public ObjectiveProgressKey getKey() {
        return key;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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
                Objects.equals(amount, other.amount);
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
        private Integer amount;

        public Builder key(ObjectiveProgressKey key) {
            this.key = key;
            return this;
        }

        public Builder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public ObjectiveProgress build() {
            return new ObjectiveProgress(this);
        }
    }

}
