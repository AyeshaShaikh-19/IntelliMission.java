package com.intellimission.model;

/**
 * TerrainMap holds a grid of TerrainCell objects.
 */
public class TerrainMap {
    private final int width;
    private final int height;
    private final TerrainCell[][] grid;

    public TerrainMap(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        this.grid = new TerrainCell[this.width][this.height];
        generateTerrain();
    }

    private void generateTerrain() {
        double defaultElevation = 1.0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new TerrainCell(x, y, defaultElevation, TerrainCell.TerrainType.PLAIN);
            }
        }
    }

    public TerrainCell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return null;
    }

    /** Convenience: movement cost at cell (x,y) or Double.POSITIVE_INFINITY if out-of-bounds/null or impassable. */
    public double getMovementCost(int x, int y) {
        TerrainCell c = getCell(x, y);
        if (c == null) return Double.POSITIVE_INFINITY;
        return c.movementCost();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
