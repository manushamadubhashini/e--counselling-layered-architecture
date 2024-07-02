package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.dto.*;
import lk.ijse.eCounselling.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private JFXComboBox cmbGender;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRid;

    @FXML
    private TextField txtStatus;

    public void initialize() {
        setGender();
    }

    private void setGender() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> status = new ArrayList<>();
        status.add("Male");
        status.add("Female");
        status.add("Other");

        for (String STATUS : status) {
            obList.add(STATUS);
        }
        cmbGender.setItems(obList);
    }



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
        cmbGender.setValue(null);
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

        if (txtId.getText().isEmpty() || txtRid.getText().isEmpty() || txtDOB.getValue() == null || txtName.getText().isEmpty() || txtDOB.getValue() == null || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() || txtStatus.getText().isEmpty() || cmbGender.getValue()==null || txtDescription.getText().isEmpty()) {
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
            } else {
                txtId.setStyle("");
            }
            if (txtRid.getText().isEmpty()) {
                txtRid.setStyle("-fx-border-color: red;");
            } else {
                txtRid.setStyle("");
            }
            if (txtDOB.getValue() == null) {
                txtDOB.setStyle("-fx-border-color: red;");
            } else {
                txtDOB.setStyle("");
            }
            if (txtName.getText().isEmpty()) {
                txtName.setStyle("-fx-border-color: red;");
            } else {
                txtName.setStyle("");
            }
            if (txtAddress.getText().isEmpty()) {
                txtAddress.setStyle("-fx-border-color: red;");
            } else {
                txtAddress.setStyle("");
            }
            if (txtContact.getText().isEmpty()) {
                txtContact.setStyle("-fx-border-color: red;");
            } else {
                txtContact.setStyle("");
            }
            if (txtStatus.getText().isEmpty()) {
                txtStatus.setStyle("-fx-border-color: red;");
            } else {
                txtStatus.setStyle("");
            }
            if (cmbGender.getValue() == null) {
                cmbGender.setStyle("-fx-border-color: red;");
            } else {
                cmbGender.setStyle("");
            }
            if (txtDescription.getText().isEmpty()) {
                txtDescription.setStyle("-fx-border-color: red;");
            } else {
                txtDescription.setStyle("");
            }


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();

            String id = txtId.getText();
            String rid = txtRid.getText();
            String name = txtName.getText();
            LocalDate DOB = txtDOB.getValue();
            Date datee = java.sql.Date.valueOf(DOB);
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String status = txtStatus.getText();
            String gender = (String) cmbGender.getValue();
            String description = txtDescription.getText();

            Patient patient = new Patient(id, name, datee, address, contact, status);
            Report report = new Report(rid, gender, description, id);

            try {
                boolean isSaved = PatientRepo.save(patient);
                boolean isSaved1 = ReportRepo.save(report);

                if (isSaved && isSaved1) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient and report saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
        String gender= (String) cmbGender.getValue();
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
    @FXML
    void DOBOnAction(ActionEvent event) {
        LocalDate dob=txtDOB.getValue();
        txtDOB.setStyle("-fx-border-color: green");
        txtAddress.requestFocus();

    }

    @FXML
    void PatientIdOnAction(ActionEvent event) {
        String pid = txtId.getText();
        if (Regex.isPatientId(pid)) {
            txtId.setStyle("-fx-border-color: green;");
            txtRid.requestFocus();
        } else {
            txtId.setStyle("-fx-border-color: red;");
            txtId.requestFocus();
        }
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        String address=txtAddress.getText();
        txtAddress.setStyle("-fx-border-color: green");
        txtContact.requestFocus();

    }
    @FXML
    void contactOnAction(ActionEvent event) {
        String contact = txtContact.getText();
        if (Regex.isContact(contact)) {
            txtContact.setStyle("-fx-border-color: green;");
            txtStatus.requestFocus();
        } else {
            txtContact.setStyle("-fx-border-color: red;");
            txtContact.requestFocus();
        }

    }

    @FXML
    void descOnAction(ActionEvent event) {
        String desc=txtDescription.getText();
        txtDescription.setStyle("-fx-border-color: green");
        txtDescription.requestFocus();

    }

    @FXML
    void genderOnAction(ActionEvent event) {
        String gender= (String) cmbGender.getValue();
        cmbGender.setStyle("-fx-border-color: green");
        txtDescription.requestFocus();

    }

    @FXML
    void nameOnAction(ActionEvent event) {
        String name=txtName.getText();
        txtName.setStyle("-fx-border-color: green");
        txtDOB.requestFocus();

    }

    @FXML
    void reportIdOnAction(ActionEvent event) {
        String rid = txtRid.getText();
        if (Regex.isReportId(rid)) {
            txtRid.setStyle("-fx-border-color: green;");
            txtName.requestFocus();
        } else {
            txtRid.setStyle("-fx-border-color: red;");
            txtRid.requestFocus();
        }

    }

    @FXML
    void statusOnAction(ActionEvent event) {
        String status=txtStatus.getText();
        txtStatus.setStyle("-fx-border-color: green");
        cmbGender.requestFocus();

    }



}
