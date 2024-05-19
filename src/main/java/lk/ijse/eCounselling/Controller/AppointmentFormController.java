package lk.ijse.eCounselling.Controller;

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
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Employee;
import lk.ijse.eCounselling.model.User;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.model.tm.EmployeeTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.EmployeeRepo;
import lk.ijse.eCounselling.repository.PatientRepo;
import lk.ijse.eCounselling.repository.UserRepo;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentFormController {

    @FXML
    public JFXComboBox cmbStatus;

    @FXML
    public JFXComboBox cmbEmployeeId;

    @FXML
    public JFXComboBox cmbPatientId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colStatus;

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
    private TextField txtDuration;

    @FXML
    private TextField txtId;


    @FXML
    private TextField txtType;

    private List<Appointment> appointmentList = new ArrayList<>();
    public void initialize() {
        this.appointmentList = getAllAppointment();
        setCellValueFactory();
        loadAppointmentTable();
        getPatientId();
        getEmployeeId();
        setStatus();
    }

    private void setStatus() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> status = new ArrayList<>();
        status.add("Completed");
        status.add("Pending");
        status.add("Cancelled");

        for (String STATUS : status) {
            obList.add(STATUS);
        }
        cmbStatus.setItems(obList);

    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = EmployeeRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbEmployeeId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPatientId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = PatientRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbPatientId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Appointment> getAllAppointment() {
        List<Appointment>  appointmentList1 = null;
        try {
            appointmentList1 = AppointmentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentList1;
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colPaId.setCellValueFactory(new PropertyValueFactory<>("pid"));
    }

    private void loadAppointmentTable() {
        ObservableList<AppointmentTm> AppointmentTMS = FXCollections.observableArrayList();


        for (Appointment appointment : appointmentList) {
            AppointmentTm appointmentTm=new AppointmentTm(
                    appointment.getId(),
                    appointment.getType(),
                    appointment.getDate(),
                    appointment.getStatus(),
                    appointment.getDuration(),
                    appointment.getEid(),
                    appointment.getPid()

            );
            AppointmentTMS.add(appointmentTm);

        }
        tblAppointment.setItems(AppointmentTMS);
        AppointmentTm selectedItem = (AppointmentTm) tblAppointment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);


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
        cmbStatus.setValue(null);
        txtDuration.setText("");
        cmbEmployeeId.setValue(null);
        cmbPatientId.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = AppointmentRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtType.getText().isEmpty() || txtDate.getValue() == null || txtDuration.getText().isEmpty() || cmbStatus.getValue() == null || cmbEmployeeId.getValue() == null || cmbPatientId.getValue() == null) {
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
            } else {
                txtId.setStyle("");
            }
            if (txtType.getText().isEmpty()) {
                txtType.setStyle("-fx-border-color: red;");
            } else {
                txtType.setStyle("");
            }
            if (txtDate.getValue() == null) {
                txtDate.setStyle("-fx-border-color: red;");
            } else {
                txtDate.setStyle("");
            }
            if (txtDuration.getText().isEmpty()) {
                txtDuration.setStyle("-fx-border-color: red;");
            } else {
                txtDuration.setStyle("");
            }
            if (cmbStatus.getValue() == null) {
                cmbStatus.setStyle("-fx-border-color: red;");
            } else {
                cmbStatus.setStyle("");
            }
            if (cmbEmployeeId.getValue() == null) {
                cmbEmployeeId.setStyle("-fx-border-color: red;");
            } else {
                cmbEmployeeId.setStyle("");
            }
            if (cmbPatientId.getValue() == null) {
                cmbPatientId.setStyle("-fx-border-color: red;");
            } else {
                cmbPatientId.setStyle("");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();

            String id = txtId.getText();
            String type = txtType.getText();
            LocalDate date = txtDate.getValue();
            Date datee = java.sql.Date.valueOf(date);
            String status = (String) cmbStatus.getValue();
            int duration = Integer.parseInt(txtDuration.getText());
            String eid = (String) cmbEmployeeId.getValue();
            String pid = (String) cmbPatientId.getValue();

            Appointment appointment = new Appointment(id, type, (java.sql.Date) datee, status, duration, eid, pid);

            try {
                boolean isSaved = AppointmentRepo.save(appointment);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "appointment saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String type = txtType.getText();
        LocalDate date = txtDate.getValue();
        Date datee = java.sql.Date.valueOf(date);
        String status= (String) cmbStatus.getValue();
        int duration= Integer.parseInt(txtDuration.getText());
        String eid= (String) cmbEmployeeId.getValue();
        String pid= (String) cmbPatientId.getValue();



        try {
            boolean isUpdated = AppointmentRepo.update(id,type, (java.sql.Date) datee,status,duration,eid,pid);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void txtAppointmentIdOnAction(ActionEvent event) {
        String appId = txtId.getText();
        if (Regex.isAppointmentId(appId)) {
            txtId.setStyle("-fx-border-color: green;");
            txtType.requestFocus();
        } else {
            txtId.setStyle("-fx-border-color: red;");
            txtId.requestFocus();
        }


    }

    public void AppointmentTypeOnAction(ActionEvent event) {
        String type=txtType.getText();
        txtType.setStyle("-fx-border-color: green");
        txtDate.requestFocus();

    }

    public void DateOnAction(ActionEvent event) {
        LocalDate date=txtDate.getValue();
        txtDate.setStyle("-fx-border-color: green");
        cmbStatus.requestFocus();
    }

    public void DurationOnAction(ActionEvent event) {
        try {
            int duration = Integer.parseInt(txtDuration.getText());
            if (Regex.isDuration(duration)) {
                txtDuration.setStyle("-fx-border-color: green;");
                cmbEmployeeId.requestFocus();
            } else {
                txtDuration.setStyle("-fx-border-color: red;");
            }
        } catch (NumberFormatException e) {
            txtDuration.setStyle("-fx-border-color: red;");
        }
    }



    public void StatusOnAction(ActionEvent event) {
        String status= (String) cmbStatus.getValue();
        cmbStatus.setStyle("-fx-border-color: green");
        txtDuration.requestFocus();
    }

    public void EIDOnAction(ActionEvent event) {
        String eid= (String) cmbEmployeeId.getValue();
        cmbEmployeeId.setStyle("-fx-border-color: green");
        cmbPatientId.requestFocus();


    }

    public void PIDOnAction(ActionEvent event) {
        String pid= (String) cmbPatientId.getValue();
        cmbPatientId.setStyle("-fx-border-color: green");


    }
}
