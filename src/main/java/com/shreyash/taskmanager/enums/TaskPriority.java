package com.shreyash.taskmanager.enums;

public enum TaskPriority {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String displayName;

    TaskPriority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
