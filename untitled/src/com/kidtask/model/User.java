package com.kidtask.model;

public class User {

    private final Role role;
    private final String name;
    private int points;

    public User(Role role, String name) {
        this.role = role;
        this.name = name;
        this.points = 0;
    }

    public Role role() {
        return role;
    }

    public String name() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }

    // LEVEL SİSTEMİ
    public int getLevel() {
        if (points < 50) return 1;
        if (points < 100) return 2;
        if (points < 150) return 3;
        if (points < 200) return 4;
        return 5;
    }

    public int levelMin() {
        return (getLevel() - 1) * 50;
    }

    public int levelMax() {
        return getLevel() * 50;
    }
}
