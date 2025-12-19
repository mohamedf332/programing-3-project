package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class DashboardController {

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/" + fxmlFile));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openStudents(ActionEvent event) {
        switchScene(event, "students/students.fxml");
    }

    @FXML
    private void openCourses(ActionEvent event) {
        switchScene(event, "courses/courses.fxml");
    }

    @FXML
    private void openEnrollment(ActionEvent event) {
        switchScene(event, "enrollment/enrollment.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene(event, "auth/login.fxml");
    }
}
