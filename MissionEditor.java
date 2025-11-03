package com.intellimission.ui;

import com.intellimission.model.MissionObjective;
import com.intellimission.model.MissionObjectiveManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Simple mission editor dialog — create new objectives and set properties.
 */
public class MissionEditor {

    public static void show(Window owner, MissionObjectiveManager mom) {
        Stage dlg = new Stage();
        dlg.initOwner(owner);
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle("Mission Editor");

        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(12);
        grid.setPadding(new Insets(12));

        TextField idField = new TextField();
        idField.setPromptText("OBJ001");
        TextField descField = new TextField();
        descField.setPromptText("Objective description");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Recon / Assault / Secure");
        Spinner<Integer> prioritySpinner = new Spinner<>(1, 10, 5);

        Label status = new Label();
        status.setStyle("-fx-text-fill: #2d6a2d; -fx-font-weight: bold;");

        Button btnAdd = new Button("Add Objective");
        btnAdd.setOnAction(e -> {
            String id = idField.getText().trim();
            String desc = descField.getText().trim();
            String cat = categoryField.getText().trim();
            int pr = prioritySpinner.getValue();
            if (id.isEmpty() || desc.isEmpty()) {
                status.setText("ID and Description required.");
                return;
            }
            MissionObjective m = new MissionObjective(id, desc, cat.isEmpty() ? "General" : cat, pr);
            mom.addObjective(m);
            status.setText("Added " + id);
            idField.clear(); descField.clear(); categoryField.clear();
        });

        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> dlg.close());

        grid.add(new Label("Objective ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descField, 1, 1);
        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryField, 1, 2);
        grid.add(new Label("Priority (1=high):"), 0, 3);
        grid.add(prioritySpinner, 1, 3);
        grid.add(btnAdd, 0, 4);
        grid.add(btnClose, 1, 4);
        grid.add(status, 0, 5, 2, 1);

        Scene sc = new Scene(grid, 480, 300);
        try {
            if (MissionEditor.class.getResource("/styles.css") != null) {
                sc.getStylesheets().add(MissionEditor.class.getResource("/styles.css").toExternalForm());
            }
        } catch (Exception ignored) {}
        dlg.setScene(sc);
        dlg.showAndWait();
    }
}
