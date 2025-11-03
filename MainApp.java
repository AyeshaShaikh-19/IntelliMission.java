package com.intellimission;

import com.intellimission.model.UnitManager;
import com.intellimission.model.TerrainMap;
import com.intellimission.ui.ControlPanel;
import com.intellimission.ui.LoginScreen;
import com.intellimission.ui.MapView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main application entry.
 * Shows login, then main UI with role-based menus.
 */
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Show login first
        LoginScreen.LoginResult lr = LoginScreen.showLogin(primaryStage);
        if (!lr.success) {
            System.out.println("Login cancelled");
            System.exit(0);
        }

        primaryStage.setTitle("IntelliMission — Mission Planner  •  User: " + lr.username + " (" + lr.role + ")");

        BorderPane root = new BorderPane();

        TerrainMap terrainMap = new TerrainMap(25, 25);
        MapView mapView = new MapView(terrainMap);
        UnitManager unitManager = new UnitManager();

        ControlPanel controlPanel = new ControlPanel(mapView, unitManager);

        root.setLeft(controlPanel.getPane());
        root.setCenter(mapView.getView());

        // Top Menu
        MenuBar mb = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        fileMenu.getItems().addAll(exit);

        Menu missionMenu = new Menu("Missions");
        MenuItem newMission = new MenuItem("New Mission...");
        newMission.setOnAction(e -> controlPanel.openMissionEditor());
        if (!"VIEWER".equalsIgnoreCase(lr.role)) {
            missionMenu.getItems().add(newMission);
        }

        mb.getMenus().addAll(fileMenu, missionMenu);
        root.setTop(mb);

        Scene scene = new Scene(root, 1200, 800);
        try {
            String css = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception ignored) {}

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
