package com.intellimission.model;

/**
 * A single terrain cell on the grid. Holds elevation and terrain type to affect movement cost.
 */
public class TerrainCell {
    public enum TerrainType { PLAIN, FOREST, MOUNTAIN, WATER, URBAN, DESERT }

    private final int x, y;
    private double elevation; // meters (for display/analysis)
    private TerrainType type;

    public TerrainCell(int x, int y, double elevation, TerrainType type) {
        this.x = x;
        this.y = y;
        this.elevation = elevation;
        this.type = type == null ? TerrainType.PLAIN : type;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public double getElevation() { return elevation; }
    public void setElevation(double elevation) { this.elevation = elevation; }

    // canonical name
    public TerrainType getType() { return type; }
    public void setType(TerrainType type) { this.type = type; }

    // convenience alias (some code expected getTerrainType)
    public TerrainType getTerrainType() { return getType(); }

    /**
     * Returns a baseline movement cost multiplier for this terrain type.
     * Higher values mean slower/harder to traverse.
     */
    public double movementCost() {
        switch (type) {
            case WATER: return Double.POSITIVE_INFINITY; // effectively impassable
            case MOUNTAIN: return 4.5;
            case FOREST: return 2.0;
            case URBAN: return 1.5;
            case DESERT: return 1.8;
            default: return 1.0; // PLAIN
        }
    }
}
