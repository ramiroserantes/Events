package com.daarthy.events.persistence.daos.mission.entities;

import com.daarthy.mini.annotations.MiniId;
import com.daarthy.mini.annotations.MiniTable;
import com.daarthy.mini.hibernate.entities.MiniEntity;
import com.daarthy.mini.shared.enums.festivals.MissionStatus;

import java.time.LocalDate;
import java.util.Objects;

@MiniTable(table = "MissionAccept")
public class MissionAcceptance extends MiniEntity {

    @MiniId
    private MissionAcceptKey key;
    private MissionStatus status;
    private LocalDate acceptDate;

    public MissionAcceptance() {}
    private MissionAcceptance(Builder builder) {
        this.key = builder.key;
        this.status = builder.status;
        this.acceptDate = builder.acceptDate;
    }

    public MissionAcceptKey getKey() {
        return key;
    }

    public void setKey(MissionAcceptKey key) {
        this.key = key;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public LocalDate getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(LocalDate acceptDate) {
        this.acceptDate = acceptDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionAcceptance other = (MissionAcceptance) o;
        return Objects.equals(key, other.key) &&
                status == other.status &&
                Objects.equals(acceptDate, other.acceptDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, status, acceptDate);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private MissionAcceptKey key;
        private MissionStatus status;
        private LocalDate acceptDate;

        public Builder key(MissionAcceptKey key) {
            this.key = key;
            return this;
        }

        public Builder status(MissionStatus status) {
            this.status = status;
            return this;
        }

        public Builder acceptDate(LocalDate acceptDate) {
            this.acceptDate = acceptDate;
            return this;
        }

        public MissionAcceptance build() {
            return new MissionAcceptance(this);
        }
    }
}
