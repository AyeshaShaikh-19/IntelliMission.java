package com.intellimission.model;

/**
 * Represents a military unit (friendly or enemy).
 * Simple POJO with basic attributes and movement helper.
 */
public class Unit {
    private final String id;
    private final String type; // e.g., "Infantry", "Tank", "Drone", "Enemy"
    private int strength;      // 0..100
    private int x;             // grid X
    private int y;             // grid Y
    private String status;     // "Active", "Engaged", "Destroyed", etc.
    private boolean friendly;  // true = friendly, false = enemy

    public Unit(String id, String type, int strength, int x, int y, boolean friendly) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
        this.id = id;
        this.type = type == null ? "Unit" : type;
        this.strength = Math.max(0, Math.min(100, strength));
        this.x = Math.max(0, x);
        this.y = Math.max(0, y);
        this.status = "Active";
        this.friendly = friendly;
    }

    // Getters & setters
    public String getId() { return id; }
    public String getType() { return type; }
    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = Math.max(0, Math.min(100, strength)); }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isFriendly() { return friendly; }
    public void setFriendly(boolean friendly) { this.friendly = friendly; }

    /** Return a readable name using ID and type */
    public String getName() {
        return type + " " + id;
    }

    /** Move unit to a new grid coordinate (keeps values non-negative). */
    public void moveTo(int newX, int newY) {
        this.x = Math.max(0, newX);
        this.y = Math.max(0, newY);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%d%%] @(%d,%d) %s",
                id, type, strength, x, y, status);
    }
}
