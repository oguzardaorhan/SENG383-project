package com.kidtask.model;

public class Wish {

    private String title;
    private String type;
    private int price;

    private int requiredLevel;
    private int requiredPoints;

    private boolean approved = false;

    public Wish(String title, String type, int price, int requiredLevel, int requiredPoints) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.requiredLevel = requiredLevel;
        this.requiredPoints = requiredPoints;
    }

    // GETTERS
    public String getTitle() { return title; }
    public String getType() { return type; }
    public int getPrice() { return price; }

    public int getRequiredLevel() { return requiredLevel; }
    public int getRequiredPoints() { return requiredPoints; }

    public boolean isApproved() { return approved; }

    // SETTERS (YENİ)
    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public void setRequiredPoints(int requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public void approve() {
        this.approved = true;
    }
}
