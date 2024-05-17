package lk.ijse.eCounselling.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.model.*;
import lk.ijse.eCounselling.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class PatientFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRid;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtId.setText("");
        txtRid.setText("");
        txtName.setText("");
        txtDOB.setValue(null);
        txtAddress.setText("");
        txtContact.setText("");
        txtStatus.setText("");
        txtGender.setText("");
        txtDescription.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String patientId = txtId.getText(); // Assuming this retrieves patient id
        String reportId = txtRid.getText(); // Assuming this retrieves report id

        try {
            boolean isPatientDeleted = PatientRepo.delete(patientId);
            boolean isReportDeleted = ReportRepo.delete(reportId);

            if (isPatientDeleted && isReportDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient and report Deleted!").show();
            } else if (isPatientDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient deleted!").show();
            } else if (isReportDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Report deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "No values deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtId.getText();
        String rid = txtRid.getText();
        String name=txtName.getText();
        LocalDate DOB = txtDOB.getValue();
        Date datee = java.sql.Date.valueOf(DOB);
        String address=txtAddress.getText();
        String contact=txtContact.getText();
        String status=txtStatus.getText();
        String gender=txtGender.getText();
        String description=txtDescription.getText();

        Patient patient=new Patient(id,name,datee,address,contact,status);
        Report report=new Report(rid,gender,description,id);

        try {
            boolean isSaved = PatientRepo.save(patient);
            boolean isSaved1= ReportRepo.save(report);

            if (isSaved && isSaved1 ) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient and report saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String rid = txtRid.getText();
        String name=txtName.getText();
        LocalDate dob = txtDOB.getValue();
        Date datee = java.sql.Date.valueOf(dob);
        String address=txtAddress.getText();
        String contact=txtContact.getText();
        String status=txtStatus.getText();
        String gender=txtGender.getText();
        String description=txtDescription.getText();


        try {
            boolean isUpdated = PatientRepo.update(id,name, (java.sql.Date) datee,address,contact,status);
            boolean isUpdate1=ReportRepo.update(rid,gender,description,id);

            if (isUpdated && isUpdate1) {
                new Alert(Alert.AlertType.CONFIRMATION, "patient and report updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
