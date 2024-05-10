package lk.ijse.eCounselling.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblAppointmentCount;

    @FXML
    private Label lblPatientCount;

    @FXML
    private Label lblSessionCount;

    private int appointmentCount;

    private int patientCount;

    private int sessionCount;

    public void initialize() {
        try {
            patientCount = getPatientCount();
            appointmentCount = getAppointmentCount();
            sessionCount=getSessionCount();

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
    void btnPatientOnAction(ActionEvent event) {


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

}









