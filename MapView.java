package com.intellimission.ui;

import com.intellimission.model.TerrainCell;
import com.intellimission.model.TerrainMap;
import com.intellimission.model.Unit;
import com.intellimission.model.UnitManager;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

/**
 * JavaFX MapView that renders the TerrainMap and units.
 * Provides methods expected by ControlPanel and MainApp.
 */
public class MapView extends Pane {

    private TerrainMap terrainMap;
    private List<Unit> friendlyUnits = Collections.emptyList();
    private List<Unit> enemyUnits = Collections.emptyList();
    private Canvas canvas;
    private static final int CELL_SIZE = 20;
    private UnitManager unitManager;

    public MapView(TerrainMap terrainMap) {
        this.terrainMap = terrainMap;
        int w = Math.max(1, terrainMap.getWidth());
        int h = Math.max(1, terrainMap.getHeight());
        canvas = new Canvas(w * CELL_SIZE, h * CELL_SIZE);
        getChildren().add(canvas);
        draw();
    }

    public Node getView() {
        return this;
    }

    public TerrainMap getTerrainMap() {
        return terrainMap;
    }

    public void setUnitManager(UnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public void updateView(TerrainMap terrainMap, List<Unit> friendlyUnits, List<Unit> enemyUnits) {
        if (terrainMap != null) this.terrainMap = terrainMap;
        this.friendlyUnits = friendlyUnits == null ? Collections.emptyList() : friendlyUnits;
        this.enemyUnits = enemyUnits == null ? Collections.emptyList() : enemyUnits;
        resizeCanvasIfNeeded();
        draw();
    }

    private void resizeCanvasIfNeeded() {
        int w = Math.max(1, terrainMap.getWidth());
        int h = Math.max(1, terrainMap.getHeight());
        if ((int)canvas.getWidth() != w * CELL_SIZE || (int)canvas.getHeight() != h * CELL_SIZE) {
            canvas.setWidth(w * CELL_SIZE);
            canvas.setHeight(h * CELL_SIZE);
        }
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        int w = terrainMap.getWidth();
        int h = terrainMap.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                TerrainCell cell = terrainMap.getCell(x, y);
                if (cell == null) continue;
                TerrainCell.TerrainType t = cell.getType();
                Color fill;
                switch (t) {
                    case PLAIN: fill = Color.LIGHTGREEN; break;
                    case MOUNTAIN: fill = Color.DARKGRAY; break;
                    case WATER: fill = Color.DEEPSKYBLUE; break;
                    case DESERT: fill = Color.BISQUE; break;
                    case FOREST: fill = Color.FORESTGREEN; break;
                    case URBAN: fill = Color.LIGHTSLATEGRAY; break;
                    default: fill = Color.GRAY; break;
                }
                gc.setFill(fill);
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // draw friendly units
        gc.setFill(Color.CYAN);
        if (friendlyUnits != null) {
            for (Unit u : friendlyUnits) {
                gc.fillOval(u.getX() * CELL_SIZE, u.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // draw enemy units
        gc.setFill(Color.RED);
        if (enemyUnits != null) {
            for (Unit u : enemyUnits) {
                gc.fillOval(u.getX() * CELL_SIZE, u.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
