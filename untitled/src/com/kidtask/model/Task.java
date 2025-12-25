package com.kidtask.model;

public class Task {

    private final String title;
    private final String type;
    private final int points;
    private TaskStatus status;
    private boolean rewarded = false;

    public Task(String title, String type, int points, TaskStatus status) {
        this.title = title;
        this.type = type;
        this.points = points;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getType() { return type; }
    public int getPoints() { return points; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public boolean isRewarded() {
        return rewarded;
    }

    public void markRewarded() {
        this.rewarded = true;
    }
}
