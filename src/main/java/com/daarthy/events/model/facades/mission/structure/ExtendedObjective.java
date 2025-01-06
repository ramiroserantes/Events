package com.daarthy.events.model.facades.mission.structure;

import com.daarthy.events.persistence.daos.objective.entities.Objective;

import java.time.LocalDate;
import java.util.Objects;

public class ExtendedObjective {
    private final Objective objective;
    private final LocalDate limitDate;

    public ExtendedObjective(Objective objective, LocalDate limitDate) {
        this.objective = objective;
        this.limitDate = limitDate;
    }

    public Objective getObjective() {
        return objective;
    }

    public boolean isExpired() {
        return limitDate.isBefore(LocalDate.now());
    }

    // *****************************************************
    // Methods
    // *****************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedObjective other = (ExtendedObjective) o;
        return Objects.equals(objective, other.objective)
                && Objects.equals(limitDate, other.limitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objective, limitDate);
    }

    @Override
    public String toString() {
        return objective.toString()
                + "limitDate: " + limitDate.toString();
    }
}
