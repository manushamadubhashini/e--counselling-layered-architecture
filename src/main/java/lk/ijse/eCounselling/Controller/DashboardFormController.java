package lk.ijse.eCounselling.Controller;

//import com.lowagie.text.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.DashBoardBO;
import lk.ijse.eCounselling.bo.custom.PatientBO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.PatientDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DashboardFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblappid;

    @FXML
    private Label lblPatientCount;

    @FXML
    private Label lblSessionCount;

    @FXML
    private Label lblHighRiskMessage;

    private int appointmentCount;

    private int patientCount;

    private int sessionCount;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;

   DashBoardBO dashBoardBO= (DashBoardBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.DASHBOARD);

   PatientBO patientBO=(PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PATIENT);

    public void initialize() throws SQLException {
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
        populateChart(barChart);
        populatePieChart(pieChart);



    }

    private void setPatientCount(int patientCount) {

        lblPatientCount.setText(String.valueOf(patientCount));
    }

    private int getPatientCount() throws SQLException {
        return dashBoardBO.getPatientCount();
    }

    private void setAppointmentCount(int appointmentCount) {
        lblappid.setText(String.valueOf(appointmentCount));
    }

    private int getAppointmentCount() throws SQLException {
        return dashBoardBO.getAppointmentCount();
    }

    private void setSessionCount(int sessionCount) {

        lblSessionCount.setText(String.valueOf(sessionCount));
    }

    private int getSessionCount() throws SQLException {
        return dashBoardBO.getSessionCount();
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
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/schedule_form.fxml"));

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
    void btnTreatmentMethodOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/treatment_method_form.fxml"));
        Stage stage=(Stage)  rootNode.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("treatment method form");
        stage.centerOnScreen();

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
            List<PatientDTO> patients = patientBO.getAll();
            boolean highRiskFound = false;
            for (PatientDTO patient : patients) {
                if (patient.getStatus().equalsIgnoreCase("High Risky Patient")) {
                    highRiskFound = true;
                    break;
                }
            }
            if (highRiskFound) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("High Risk Patients Alert");
                    alert.setHeaderText("High-risk patients found!");
                    alert.setContentText("There are high-risk patients in the database. Immediate attention is required!");
                    alert.showAndWait();
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    private void populateChart(BarChart<String, Number> barChart) throws SQLException {
        dashBoardBO.getAppointmentCount(barChart);
        // Define an array of colors
        String[] colors = {"#AFDBF5", "#FFD700", "#FF6347", "#4CBB17", "#0096FF"};

        // Wait for the chart to be rendered
        Platform.runLater(() -> {
            int colorIndex = 0;
            for (Node n : barChart.lookupAll(".chart-bar")) {
                n.setStyle("-fx-bar-fill: " + colors[colorIndex % colors.length] + ";");
                colorIndex++;
            }
        });

    }
    private void populatePieChart(PieChart pieChart) throws SQLException {
        dashBoardBO.getTreatmentCount(pieChart);
    }

}












