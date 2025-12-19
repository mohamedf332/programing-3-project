package controller;

import com.solt.jdc.entity.Course;
import com.solt.jdc.service.CourseService;
import com.solt.jdc.utili.Validator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoursesController {

    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, Integer> idColumn;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, String> durationColumn;
    @FXML
    private TableColumn<Course, Integer> feesColumn;
    @FXML
    private TableColumn<Course, String> remarkColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private TextField feesTextField;
    @FXML
    private TextField remarkTextField;

    private CourseService courseService = new CourseService();

    // Initialize TableView columns and load data
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feesColumn.setCellValueFactory(new PropertyValueFactory<>("fees"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));

        loadCourses();
    }

    // Load all courses into TableView
    private void loadCourses() {
        coursesTable.setItems(FXCollections.observableArrayList(courseService.findAll()));
    }

    // Add new course
    @FXML
    private void addCourse(ActionEvent event) {
        try {
            Course course = new Course();
            course.setName(nameTextField.getText());
            course.setDuration(durationTextField.getText());
            course.setFees(Integer.parseInt(feesTextField.getText()));
            course.setRemark(remarkTextField.getText());

            int result = courseService.add(course);
            if (result > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
                clearFields();
                loadCourses();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Fees must be a number.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Edit selected course
    @FXML
    private void editCourse(ActionEvent event) {
        Course selected = coursesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to edit.");
            return;
        }

        try {
            selected.setName(nameTextField.getText());
            selected.setDuration(durationTextField.getText());
            selected.setFees(Integer.parseInt(feesTextField.getText()));
            selected.setRemark(remarkTextField.getText());

            int updated = courseService.update(selected);
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully!");
                clearFields();
                loadCourses();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Fees must be a number.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete selected course
    @FXML
    private void deleteCourse(ActionEvent event) {
        Course selected = coursesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to delete.");
            return;
        }

        try {
            int deleted = courseService.deleteById(selected.getId());
            if (deleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully!");
                loadCourses();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Navigate back to dashboard
    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear input fields
    private void clearFields() {
        nameTextField.clear();
        durationTextField.clear();
        feesTextField.clear();
        remarkTextField.clear();
    }
}
