package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.eCounselling.bo.custom.AppointmentBO;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.EmployeeBO;
import lk.ijse.eCounselling.dto.*;
import lk.ijse.eCounselling.dto.tm.AppointmentTm;
import lk.ijse.eCounselling.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentFormController {

    @FXML
    private JFXButton btnAddNewItem;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox cmbTime;

    @FXML
    public JFXComboBox cmbEmployeeId;

    @FXML
    public JFXComboBox cmbPatientId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colPaId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<AppointmentTm> tblAppointment;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtType;

    AppointmentBO appointmentBO= (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.APPOINTMENT);
    EmployeeBO employeeBO=(EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize() {
        txtId.setDisable(true);
        txtType.setDisable(true);
        txtDate.setDisable(true);
        cmbTime.setDisable(true);
        cmbEmployeeId.setDisable(true);
        cmbPatientId.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.setEditable(false);
        txtDate.setEditable(false);
        setCellValueFactory();
        loadAppointmentTable();
        getPatientId();
        getEmployeeId();
        setTime();
        txtDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Optional styling for disabled dates
                        }
                    }
                };
            }
        });

        tblAppointment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            //btnUpdate.setText(newValue != null ? "Update" : "Save");
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtType.setText(newValue.getType());
                txtDate.setValue(newValue.getDate());
                cmbTime.setValue(newValue.getTime());
                cmbEmployeeId.setValue(newValue.getEid());
                cmbPatientId.setValue(newValue.getPid());

                txtId.setDisable(false);
                txtType.setDisable(false);
                txtDate.setDisable(false);
                cmbTime.setDisable(false);
                cmbEmployeeId.setDisable(false);
                cmbPatientId.setDisable(false);


            }
        });
    }

    private void setTime() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> time = new ArrayList<>();
        time.add("6.30 p.m-8.30 p.m");
        time.add("7.00 a.m-9.00 a.m");
        time.add("10.00 p.m-11.00 p.m");

        for (String Time : time) {
            obList.add(Time);
        }
        cmbTime.setItems(obList);

    }

    private void getEmployeeId() {
        try {
          ArrayList<EmployeeDTO> employees= employeeBO.getAll();
            for (EmployeeDTO e : employees) {
                cmbEmployeeId.getItems().add(e.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPatientId() {
        try {
        ArrayList<Patient> patients= PatientRepo.getAll();
        for (Patient p:patients){
            cmbPatientId.getItems().add(p.getId());

        }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colPaId.setCellValueFactory(new PropertyValueFactory<>("pid"));
    }

    private void loadAppointmentTable(){
       tblAppointment.getItems().clear();
       try {
           ArrayList<AppointmentDTO> appointments=appointmentBO.getAll();
           for (AppointmentDTO a:appointments){
               tblAppointment.getItems().add(new AppointmentTm(a.getId(),a.getType(),a.getDate(),a.getTime(),a.getEid(),a.getPid()));
           }
       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
       }

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
        txtType.setText("");
        txtDate.setValue(null);
        cmbTime.setValue(null);
        cmbEmployeeId.setValue(null);
        cmbPatientId.setValue(null);
        tblAppointment.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblAppointment.getSelectionModel().getSelectedItem().getId();

        try {
            boolean isDeleted = appointmentBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment deleted!").show();
            }
            tblAppointment.getItems().remove(tblAppointment.getSelectionModel().getSelectedItem());
            tblAppointment.getSelectionModel().clearSelection();
            txtId.clear();
            txtType.clear();
            txtDate.setValue(null);
            cmbTime.setValue(null);
            cmbEmployeeId.setValue(null);
            cmbPatientId.setValue(null);
            init();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean hasError = false;

        if (txtId.getText().isEmpty()) {
            txtId.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            txtId.setStyle("");
        }
        if (txtType.getText().isEmpty()) {
            txtType.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            txtType.setStyle("");
        }
        if (txtDate.getValue()==null) {
            txtDate.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            txtDate.setStyle("");
        }
        if (cmbTime.getValue() == null) {
            cmbTime.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            cmbTime.setStyle("");
        }
        if (cmbEmployeeId.getValue() == null) {
            cmbEmployeeId.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            cmbEmployeeId.setStyle("");
        }
        if (cmbPatientId.getValue() == null) {
            cmbPatientId.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            cmbPatientId.setStyle("");
        }

        // If there is an error, show an alert and return
        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
            String id = txtId.getText();
            String type = txtType.getText();
            LocalDate date=txtDate.getValue();
            String time = (String) cmbTime.getValue();
            String eid = (String) cmbEmployeeId.getValue();
            String pid = (String) cmbPatientId.getValue();


            if(! type.matches("[A-Za-z ]+")){
                new Alert(Alert.AlertType.ERROR,"invalid value").show();
                txtType.setStyle("-fx-border-color: red");
                txtType.requestFocus();
                return;
            }

            AppointmentDTO appointment = new AppointmentDTO(id, type, date, time, eid, pid);

            try {
                boolean isSaved = appointmentBO.save(appointment);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "appointment saved!").show();
                    init();
                }
                tblAppointment.getItems().add(new AppointmentTm(id,type,date,time,eid,pid));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String type = txtType.getText();
        LocalDate date=txtDate.getValue();
        String time= (String) cmbTime.getValue();
        String eid= (String) cmbEmployeeId.getValue();
        String pid= (String) cmbPatientId.getValue();

        if(! type.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR,"invalid value").show();
            txtType.setStyle("-fx-border-color: red");
            txtType.requestFocus();
            return;
        }
        AppointmentDTO appointmentDTO=new AppointmentDTO(id,type,date,time,eid,pid);
        try {
            boolean isUpdated = appointmentBO.update(appointmentDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment updated!").show();
            }
            AppointmentTm selectedAppo=tblAppointment.getSelectionModel().getSelectedItem();
            selectedAppo.setType(type);
            selectedAppo.setDate(date);
            selectedAppo.setTime(time);
            selectedAppo.setEid(eid);
            selectedAppo.setPid(pid);
            tblAppointment.refresh();
            tblAppointment.getSelectionModel().clearSelection();
            init();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
    @FXML
    void btnNewAppointmentOnAction(ActionEvent event) {
        txtId.clear();
        txtType.clear();
        txtDate.setValue(null);
        cmbTime.setValue(null);
        cmbEmployeeId.setValue(null);
        cmbPatientId.setValue(null);
        txtId.setDisable(false);
        txtType.setDisable(false);
        txtDate.setDisable(false);
        cmbTime.setDisable(false);
        cmbEmployeeId.setDisable(false);
        cmbPatientId.setDisable(false);
        txtId.setText(generateNewId());
        txtId.setEditable(false);
        btnSave.setDisable(false);
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return appointmentBO.generateId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        }
        return "A001";

    }
    private void init(){
        txtId.setDisable(true);
        txtType.setDisable(true);
        txtDate.setDisable(true);
        cmbTime.setDisable(true);
        cmbEmployeeId.setDisable(true);
        cmbPatientId.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }

}
