package com.intellimission.ui;

import com.intellimission.model.Unit;
import com.intellimission.model.UnitManager;
import com.intellimission.services.ThreatAnalyzer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

/**
 * Control panel (JavaFX) to operate the map and units.
 */
public class ControlPanel extends VBox {

    private final MapView mapView;
    private final UnitManager unitManager;
    private final ThreatAnalyzer threatAnalyzer;

    public ControlPanel(MapView mapView, UnitManager unitManager) {
        this.mapView = mapView;
        this.unitManager = unitManager;
        this.threatAnalyzer = new ThreatAnalyzer();

        mapView.setUnitManager(unitManager);

        setSpacing(12);
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom, #eef2e0, #d9e2c4);"
                + "-fx-border-color: #7a8a67; -fx-border-radius: 6; -fx-border-width: 2;");

        buildUI();
    }

    private void buildUI() {
        Label title = new Label("Mission Control Panel");
        title.setFont(Font.font("Segoe UI Semibold", 16));
        title.setTextFill(Color.web("#37472b"));

        Button refreshButton = createButton("🔄 Refresh Map");
        refreshButton.setOnAction(e -> mapView.updateView(
                mapView.getTerrainMap(),
                unitManager.getFriendlyUnits(),
                unitManager.getEnemyUnits()
        ));

        Button addUnitButton = createButton("➕ Add Mock Unit");
        addUnitButton.setOnAction(e -> addMockUnit());

        Button analyzeThreatsButton = createButton("⚠️ Analyze Threats");
        analyzeThreatsButton.setOnAction(e -> analyzeThreats());

        Button clearButton = createButton("🧹 Clear Units");
        clearButton.setOnAction(e -> clearUnits());

        getChildren().addAll(
                title,
                new Separator(),
                refreshButton,
                addUnitButton,
                analyzeThreatsButton,
                clearButton
        );
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", 13));
        button.setStyle("-fx-background-color: #b6d7a8; -fx-text-fill: #1b3810; -fx-background-radius: 8; -fx-padding: 6 14;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #a4c49b; -fx-text-fill: #1b3810; -fx-background-radius: 8;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #b6d7a8; -fx-text-fill: #1b3810; -fx-background-radius: 8;"));
        return button;
    }

    private void addMockUnit() {
        Random rand = new Random();
        Unit newUnit = new Unit(
                "F" + (unitManager.getFriendlyUnits().size() + 1),
                "Infantry",
                50 + rand.nextInt(51), // strength 50..100
                rand.nextInt(mapView.getTerrainMap().getWidth()),
                rand.nextInt(mapView.getTerrainMap().getHeight()),
                true
        );
        unitManager.addFriendlyUnit(newUnit);
        mapView.updateView(mapView.getTerrainMap(), unitManager.getFriendlyUnits(), unitManager.getEnemyUnits());
    }

    private void analyzeThreats() {
        threatAnalyzer.analyze(unitManager.getFriendlyUnits(), unitManager.getEnemyUnits());
        mapView.updateView(mapView.getTerrainMap(), unitManager.getFriendlyUnits(), unitManager.getEnemyUnits());
    }

    private void clearUnits() {
        unitManager.clearAll();
        mapView.updateView(mapView.getTerrainMap(), unitManager.getFriendlyUnits(), unitManager.getEnemyUnits());
    }

    // For embedding into main layout
    public VBox getPane() {
        return this;
    }

    public void openMissionEditor() {
        System.out.println("Mission editor opened (placeholder)");
    }
}
