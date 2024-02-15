package com.daarthy.events.app.modules.missions;

public enum Grade {

    F("F", 1, 20,100.0F),
    E("E", 2, 15,40.0F),
    D("D", 3, 10,-5.0F),
    C("C", 4, 7, -100F),
    B("B", 5, 5, -100F),
    A("A", 6, 3, -100F),
    S("S", 7, 1, -100F);

    private final String grade;
    private final int priority;
    private final int completions;
    private final Float probability;

    Grade(String grade, int priority, int completions, Float probability) {
        this.grade = grade;
        this.priority = priority;
        this.completions = completions;
        this.probability = probability;
    }

    public String getGradeString() {
        return grade;
    }

    public int getPriority() {
        return priority;
    }

    public int getCompletions() {
        return completions;
    }

    public Float getProbability() {
        return probability;
    }
}
