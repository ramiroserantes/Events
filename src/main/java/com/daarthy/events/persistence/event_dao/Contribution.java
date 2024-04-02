package com.daarthy.events.persistence.event_dao;

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

    public void increaseMedals() {
        this.medals += 1;
    }

    public int getItems() {
        return items;
    }

    public int getMedals() {
        return medals;
    }

}
