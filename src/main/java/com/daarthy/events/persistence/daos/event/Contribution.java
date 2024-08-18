package com.daarthy.events.persistence.daos.event;

public class Contribution {

    private int items = 0;
    private int medals = 0;

    public Contribution() {}

    public Contribution(int items, int medals) {
        this.items = items;
        this.medals = medals;
    }

    public void increaseItems() {
        this.items += 1;
    }

    public void increaseMedals(int limit) {
        if(this.medals < limit + 1) {
            this.medals += 1;
        }
    }

    public int getItems() {
        return items;
    }

    public int getMedals() {
        return medals;
    }

    public int getMedalsToSave(int limit) {
        return Math.min(medals, limit);
    }

}
