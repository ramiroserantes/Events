package com.daarthy.events.persistence.daos.objective.entities;

import com.daarthy.mini.annotations.MiniColumn;
import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.hibernate.entities.MiniEntity;
import com.daarthy.mini.shared.classes.enums.festivals.ActionType;

import java.util.Objects;

public class Objective extends MiniEntity {

    @MiniId(generated = true)
    private Long id;
    private Long missionId;
    private ActionType actionType;
    @MiniColumn(column = "reqAmount")
    private int requiredAmount;
    private String target;
    private Integer levels;

    public Objective () {}

    private Objective(Builder builder) {
        this.id = builder.id;
        this.missionId = builder.missionId;
        this.actionType = builder.actionType;
        this.requiredAmount = builder.requiredAmount;
        this.target = builder.target;
        this.levels = builder.levels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public void setRequiredAmount(int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objective other = (Objective) o;
        return Objects.equals(id, other.id)
                && Objects.equals(missionId, other.missionId)
                && Objects.equals(actionType, other.actionType)
                && requiredAmount == other.requiredAmount
                && Objects.equals(target, other.target)
                && Objects.equals(levels, other.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, missionId, actionType, requiredAmount, target, levels);
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
        private Long id;
        private Long missionId;
        private ActionType actionType;
        private int requiredAmount;
        private String target;
        private Integer levels;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder missionId(Long missionId) {
            this.missionId = missionId;
            return this;
        }

        public Builder actionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder requiredAmount(int requiredAmount) {
            this.requiredAmount = requiredAmount;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public Builder levels(Integer levels) {
            this.levels = levels;
            return this;
        }

        public Objective build() {
            return new Objective(this);
        }
    }
}
