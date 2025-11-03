package com.intellimission.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight login dialog with in-memory sample users.
 */
public class LoginScreen {

    public static class LoginResult {
        public final boolean success;
        public final String username;
        public final String role;
        public LoginResult(boolean s, String u, String r) { success = s; username = u; role = r; }
    }

    private static Map<String, String> users = new HashMap<>();
    private static Map<String, String> roles = new HashMap<>();
    static {
        users.put("admin", "admin123");
        roles.put("admin", "ADMIN");
        users.put("planner", "planner123");
        roles.put("planner", "PLANNER");
        users.put("viewer", "viewer123");
        roles.put("viewer", "VIEWER");
    }

    public static LoginResult showLogin(Window owner) {
        Stage dlg = new Stage();
        dlg.initOwner(owner);
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle("IntelliMission — Login");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(14));

        Label uLabel = new Label("Username:");
        TextField uField = new TextField();
        uField.setPromptText("e.g. planner");

        Label pLabel = new Label("Password:");
        PasswordField pField = new PasswordField();
        pField.setPromptText("password");

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: #8b0000;");

        Button btnLogin = new Button("Login");
        Button btnCancel = new Button("Cancel");

        grid.add(uLabel, 0, 0);
        grid.add(uField, 1, 0);
        grid.add(pLabel, 0, 1);
        grid.add(pField, 1, 1);
        grid.add(btnLogin, 0, 2);
        grid.add(btnCancel, 1, 2);
        grid.add(msg, 0, 3, 2, 1);

        Scene sc = new Scene(grid);
        try {
            if (LoginScreen.class.getResource("/styles.css") != null) {
                sc.getStylesheets().add(LoginScreen.class.getResource("/styles.css").toExternalForm());
            }
        } catch (Exception ignored) {}

        dlg.setScene(sc);

        final LoginResult[] result = new LoginResult[1];

        btnLogin.setOnAction(ev -> {
            String u = uField.getText().trim();
            String p = pField.getText().trim();
            if (users.containsKey(u) && users.get(u).equals(p)) {
                result[0] = new LoginResult(true, u, roles.getOrDefault(u, "PLANNER"));
                dlg.close();
            } else {
                msg.setText("Invalid credentials. Try admin/admin123");
            }
        });

        btnCancel.setOnAction(ev -> {
            result[0] = new LoginResult(false, null, null);
            dlg.close();
        });

        dlg.showAndWait();
        return result[0] == null ? new LoginResult(false, null, null) : result[0];
    }
}
