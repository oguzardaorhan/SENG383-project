package com.kidtask.model;

public enum TaskStatus {
    TODO("Todo"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
    REJECTED("Rejected");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
