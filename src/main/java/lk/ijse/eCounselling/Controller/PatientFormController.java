package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.PatientBO;
import lk.ijse.eCounselling.bo.custom.ReportBO;
import lk.ijse.eCounselling.dto.*;
import com.jfoenix.controls.JFXButton;

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
    private JFXComboBox cmbStatus;

    @FXML
    private JFXButton btnNewPatient;

    @FXML
    private JFXButton btnSaved;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    PatientBO patientBO= (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PATIENT);
    ReportBO reportBO=(ReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.REPORT);

    public void initialize() {
        setGender();
        btnSaved.setDisable(true);
        setStatus();

        txtDOB.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Optional styling for disabled dates
                        }
                    }
                };
            }
        });

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
    private void setStatus(){
        ObservableList<String> observableList=FXCollections.observableArrayList();
        List<String> status=new ArrayList<>();
        status.add("High Risky PatientDTO");
        status.add("Moderate Risky PatientDTO");
        status.add("Low Risky PatientDTO");
        for (String STATUS:status){
            observableList.add(STATUS);
        }
        cmbStatus.setItems(observableList);
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
        cmbStatus.setValue(null);
        cmbGender.setValue(null);
        txtDescription.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String patientId = txtId.getText(); // Assuming this retrieves patient id
        String reportId = txtRid.getText(); // Assuming this retrieves report id
        try {
            boolean isDeleted= patientBO.delete(patientId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient and report Deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

            boolean hasError=false;
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtId.setStyle("");
            }
            if (txtRid.getText().isEmpty()) {
                txtRid.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtRid.setStyle("");
            }
            if (txtDOB.getValue() == null) {
                txtDOB.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtDOB.setStyle("");
            }
            if (txtName.getText().isEmpty()) {
                txtName.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtName.setStyle("");
            }
            if (txtAddress.getText().isEmpty()) {
                txtAddress.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtAddress.setStyle("");
            }
            if (txtContact.getText().isEmpty()) {
                txtContact.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtContact.setStyle("");
            }
            if (cmbStatus.getValue() == null) {
                cmbStatus.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                cmbStatus.setStyle("");
            }
            if (cmbGender.getValue() == null) {
                cmbGender.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                cmbGender.setStyle("");
            }
            if (txtDescription.getText().isEmpty()) {
                txtDescription.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtDescription.setStyle("");
            }
             if(hasError) {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText(null);
                 alert.setContentText("Please fill in all fields.");
                 alert.show();
             }
            String id=txtId.getText();
            String rid = txtRid.getText();
            String name = txtName.getText();
            LocalDate DOB = txtDOB.getValue();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String status = (String) cmbStatus.getValue();
            String gender = (String) cmbGender.getValue();
            String description = txtDescription.getText();
            List<ReportDTO> reportDTOList=new ArrayList<>();
            reportDTOList.add(new ReportDTO(rid,gender,description,id));

            if(! name.matches("[A-Za-z ]+")){
                new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red");
                return;
            }
            if (!address.matches(".{3,}")) {
                new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
                txtAddress.requestFocus();
                txtAddress.setStyle("-fx-border-color: red");
                return;
            }
            if(!contact.matches("^0[0-9]{9}$")){
                new Alert(Alert.AlertType.ERROR,"Invalid Phone Number").show();
                txtContact.requestFocus();
                txtContact.setStyle("-fx-border-color: red");
                return;
            }
           if(! name.matches("[A-Za-z ]+")){
                new Alert(Alert.AlertType.ERROR,"Invalid description").show();
                txtDescription.requestFocus();
                txtDescription.setStyle("-fx-border-color: red");

           }
           try {
               boolean isSaved= patientBO.save(new PatientDTO(id,name,DOB,address,contact,status));
               boolean isSaved1= reportBO.save(new ReportDTO(rid,gender,description,id));
               if (isSaved && isSaved1) {
                   new Alert(Alert.AlertType.CONFIRMATION, "PatientDTO and report saved!").show();
                   btnSaved.setDisable(true);
               }
           }catch (SQLException e){
               new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
           }




    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        String rid = txtRid.getText();
        String name=txtName.getText();
        LocalDate dob = txtDOB.getValue();
        String address=txtAddress.getText();
        String contact=txtContact.getText();
        String status= (String) cmbStatus.getValue();
        String gender= (String) cmbGender.getValue();
        String description=txtDescription.getText();
        List<ReportDTO> reportDTOList=new ArrayList<>();
        reportDTOList.add(new ReportDTO(rid,gender,description,id));
        if(! name.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color: red");
            return;
        }
        if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color: red");
            return;
        }
        if(!contact.matches("^0[0-9]{9}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Phone Number").show();
            txtContact.requestFocus();
            txtContact.setStyle("-fx-border-color: red");
            return;
        }
        if(! name.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR,"Invalid description").show();
            txtDescription.requestFocus();
            txtDescription.setStyle("-fx-border-color: red");
            return;
        }

            boolean isUpdated = patientBO.update(new PatientDTO(id, name, dob, address, contact, status));
            boolean isUpdated1 = reportBO.update(new ReportDTO(rid, gender, description, id));
            if (isUpdated && isUpdated1) {
                new Alert(Alert.AlertType.CONFIRMATION, "patient and report updated!").show();
            }else if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "patient updated!").show();
            }else if(isUpdated1){
                new Alert(Alert.AlertType.CONFIRMATION, "report updated!").show();
           }else{
            new Alert(Alert.AlertType.ERROR,"Invalid value!").show();
        }

    }

    @FXML
    void btnNewPatientOnAction(ActionEvent event) {
        txtId.setText(generateId());
        txtId.setEditable(false);
        btnSaved.setDisable(false);
        txtRid.setText(generateRid());
        txtRid.setEditable(false);
        init();

    }

    private String generateId(){
        try {
            return patientBO.generateId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        }
        return "P001";

    }
    private String generateRid(){
        try {
            return reportBO.generateId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new rid " + e.getMessage()).show();

        }
        return "R001";
    }
    private void init(){
        txtId.setEditable(true);
        txtRid.setEditable(true);
    }



}
