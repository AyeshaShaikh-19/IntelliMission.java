package com.intellimission.model;

/**
 * Represents a mission objective with ID, description, category, priority and completion flag.
 */
public class MissionObjective {
    private final String id;
    private String description;
    private String category;
    private int priority; // 1..10 (1 = highest)
    private boolean completed;

    public MissionObjective(String id, String description, String category, int priority) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
        this.id = id;
        this.description = description;
        this.category = category;
        this.priority = Math.max(1, Math.min(10, priority));
        this.completed = false;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public String getCategory() { return category; }
    public void setCategory(String c) { this.category = c; }
    public int getPriority() { return priority; }
    public void setPriority(int p) { this.priority = Math.max(1, Math.min(10, p)); }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean b) { this.completed = b; }

    @Override
    public String toString() {
        return String.format("%s: %s [%s] prio=%d %s", id, description, category, priority, completed ? "(done)" : "");
    }
}
