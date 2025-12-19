package controller;

import com.solt.jdc.entity.Student;
import com.solt.jdc.service.StudentService;
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

public class StudentsController {

    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;

    private StudentService studentService = new StudentService();

    // Initialize TableView columns and load data
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadStudents();
    }

    // Load all students into TableView
    private void loadStudents() {
        studentsTable.setItems(FXCollections.observableArrayList(studentService.findAll()));
    }

    // Add new student
    @FXML
    private void addStudent(ActionEvent event) {
        try {
            Student stu = new Student();
            stu.setName(nameTextField.getText());
            stu.setPhone(phoneTextField.getText());
            stu.setEmail(emailTextField.getText());
            stu.setAddress(addressTextField.getText());

            int id = studentService.add(stu);
            if (id > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
                clearFields();
                loadStudents();
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Edit selected student
    @FXML
    private void editStudent(ActionEvent event) {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to edit.");
            return;
        }

        try {
            selected.setName(nameTextField.getText());
            selected.setPhone(phoneTextField.getText());
            selected.setEmail(emailTextField.getText());
            selected.setAddress(addressTextField.getText());

            int updated = studentService.update(selected);
            if (updated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully!");
                clearFields();
                loadStudents();
            }
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete selected student
    @FXML
    private void deleteStudent(ActionEvent event) {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to delete.");
            return;
        }

        try {
            int deleted = studentService.deleteById(selected.getId());
            if (deleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully!");
                loadStudents();
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
        phoneTextField.clear();
        emailTextField.clear();
        addressTextField.clear();
    }
}
