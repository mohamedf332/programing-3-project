package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entity.Course;
import model.entity.Registration;
import model.entity.Student;
import service.CourseService;
import service.RegistrationService;
import service.StudentService;

public class EnrollmentController {

    @FXML
    private TableView<Registration> registrationTable;
    @FXML
    private TableColumn<Registration, Integer> idColumn;
    @FXML
    private TableColumn<Registration, String> studentColumn;
    @FXML
    private TableColumn<Registration, String> courseColumn;
    @FXML
    private TableColumn<Registration, Integer> feesColumn;
    @FXML
    private TableColumn<Registration, Integer> paidColumn;
    @FXML
    private TableColumn<Registration, String> dateColumn;

    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private ComboBox<Course> courseComboBox;
    @FXML
    private TextField feesTextField;
    @FXML
    private TextField paidTextField;

    private RegistrationService registrationService = new RegistrationService();
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("className")); // Used className to store courseName
        feesColumn.setCellValueFactory(new PropertyValueFactory<>("fees"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateStr"));

        loadData();
    }

    private void loadData() {
        registrationTable.setItems(FXCollections.observableArrayList(registrationService.findAll()));
        studentComboBox.setItems(FXCollections.observableArrayList(studentService.findAll()));
        courseComboBox.setItems(FXCollections.observableArrayList(courseService.findAll()));
    }

    @FXML
    private void enroll(ActionEvent event) {
        try {
            Student selectedStudent = studentComboBox.getValue();
            Course selectedCourse = courseComboBox.getValue();

            if (selectedStudent == null || selectedCourse == null) {
                showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select both Student and Course.");
                return;
            }

            int fees = Integer.parseInt(feesTextField.getText());
            int paid = Integer.parseInt(paidTextField.getText());

            Registration reg = new Registration();
            reg.setStudentId(selectedStudent.getId());
            reg.setClassId(selectedCourse.getId()); // Mapping Course ID to Class ID
            reg.setFees(fees);
            reg.setPaid(paid);

            int result = registrationService.add(reg);
            if (result > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Enrollment successful!");
                loadData();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to enroll. Check database connection.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Fees and Paid must be numbers.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        studentComboBox.getSelectionModel().clearSelection();
        courseComboBox.getSelectionModel().clearSelection();
        feesTextField.clear();
        paidTextField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard/dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
