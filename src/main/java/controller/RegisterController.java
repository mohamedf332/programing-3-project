package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{5,15}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            errorLabel.setText("Please fill all fields");
            return;
        }

        if (!username.matches(USERNAME_REGEX)) {
            errorLabel.setText("Invalid username format");
            return;
        }

        if (!password.matches(PASSWORD_REGEX)) {
            errorLabel.setText("Invalid password format");
            return;
        }

        if (!password.equals(confirm)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        // Dummy Register
        errorLabel.setText("Registered successfully ✔");
    }

    @FXML
    private void backToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/auth/login.fxml"));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
