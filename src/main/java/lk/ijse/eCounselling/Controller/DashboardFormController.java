package lk.ijse.eCounselling.Controller;

//import com.lowagie.text.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Patient;
import lk.ijse.eCounselling.model.Treatment;
import lk.ijse.eCounselling.repository.PatientRepo;
import lk.ijse.eCounselling.repository.TreatmentRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DashboardFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblAppointmentCount;

    @FXML
    private Label lblPatientCount;

    @FXML
    private Label lblSessionCount;

    @FXML
    private Label lblHighRiskMessage;

    private int appointmentCount;

    private int patientCount;

    private int sessionCount;

    public void initialize() {
        try {
            patientCount = getPatientCount();
            appointmentCount = getAppointmentCount();
            sessionCount = getSessionCount();
            checkForHighRiskPatients();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setPatientCount(patientCount);
        setAppointmentCount(appointmentCount);
        setSessionCount(sessionCount);

    }

    private void setPatientCount(int patientCount) {

        lblPatientCount.setText(String.valueOf(patientCount));
    }

    private int getPatientCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS patient_count FROM patient";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int patientCount = 0;
        if (resultSet.next()) {
            patientCount = resultSet.getInt("patient_count");
        }
        return patientCount;
    }

    private void setAppointmentCount(int appointmentCount) {
        lblAppointmentCount.setText(String.valueOf(appointmentCount));
    }

    private int getAppointmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS appointment_count FROM appointment";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int appointmentCount = 0;
        if (resultSet.next()) {
            appointmentCount = resultSet.getInt("appointment_count");
        }
        return appointmentCount;
    }

    private void setSessionCount(int sessionCount) {

        lblSessionCount.setText(String.valueOf(sessionCount));
    }

    private int getSessionCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS session_count FROM  sessions  ";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int sessionCount = 0;
        if (resultSet.next()) {
            sessionCount = resultSet.getInt("session_count");
        }
        return sessionCount;
    }

    @FXML
    void btnAppointmentFormOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/appointment_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("appointment Form");


    }

    @FXML
    void btnEmployeeFormOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("employee Form");

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("login Form");


    }

    @FXML
    void btnPatientOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/patientView_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("patient Form");


    }

    @FXML
    void btnScheduleOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/schedule_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("schedule Form");


    }

    @FXML
    void btnSessionOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/session_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("session Form");


    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnTreatmentOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/treatment_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("treatment Form");


    }

    private void checkForHighRiskPatients() {
            try {
                List<Patient> patients = PatientRepo.getAll();
                boolean highRiskFound = false;

                for (Patient patient : patients) {
                    if (patient.getStatus().equalsIgnoreCase("High Risky Patient")) {
                        highRiskFound = true;
                        break; // Exit loop once a high-risk patient is found
                    }
                }

                if (highRiskFound) {
                    // Defer showing the alert until after the dashboard is loaded
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("High Risk Patients Alert");
                        alert.setHeaderText("High-risk patients found!");
                        alert.setContentText("There are high-risk patients in the database. Immediate attention is required!");
                        alert.showAndWait();
                    });

                    // Update UI on the JavaFX Application Thread

                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle database exception appropriately
            }
        }
    }











