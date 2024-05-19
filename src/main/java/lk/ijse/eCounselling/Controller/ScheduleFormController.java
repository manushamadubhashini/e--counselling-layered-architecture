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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Schedule;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.model.tm.ScheduleTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.EmployeeRepo;
import lk.ijse.eCounselling.repository.ScheduleRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox cmbEmpId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEtime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colStime;

    @FXML
    private TableColumn<?, ?> coleid;


    @FXML
    private TableView<ScheduleTm> tblSchedule;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtStartTime;

    private List<Schedule> scheduleList = new ArrayList<>();
    public void initialize() {
        this.scheduleList = getAllSchedule();
        setCellValueFactory();
        loadScheduleTable();
        getEmployeeId();
    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = EmployeeRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbEmpId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadScheduleTable() {
        ObservableList<ScheduleTm> ScheduleTMS = FXCollections.observableArrayList();


        for (Schedule schedule : scheduleList) {
            ScheduleTm scheduleTm=new ScheduleTm(
                    schedule.getId(),
                    schedule.getDate(),
                    schedule.getStime(),
                    schedule.getEtime(),
                    schedule.getEid()

            );
            ScheduleTMS.add(scheduleTm);

        }
        tblSchedule.setItems(ScheduleTMS);
        ScheduleTm selectedItem = (ScheduleTm) tblSchedule.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStime.setCellValueFactory(new PropertyValueFactory<>("stime"));
        colEtime.setCellValueFactory(new PropertyValueFactory<>("etime"));
        coleid.setCellValueFactory(new PropertyValueFactory<>("eid"));

    }

    private List<Schedule> getAllSchedule() {
        List<Schedule>  scheduleList1 = null;
        try {
            scheduleList1 = ScheduleRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scheduleList1;


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
        txtDate.setValue(null);
        txtStartTime.setText("");
        txtEndTime.setText("");
        cmbEmpId.setValue(null);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtDate.getValue() == null || txtStartTime.getText().isEmpty() || txtEndTime.getText().isEmpty() || cmbEmpId.getValue() == null) {
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
            } else {
                txtId.setStyle("");
            }
            if (txtStartTime.getText().isEmpty()) {
                txtStartTime.setStyle("-fx-border-color: red;");
            } else {
                txtStartTime.setStyle("");
            }
            if (txtDate.getValue() == null) {
                txtDate.setStyle("-fx-border-color: red;");
            } else {
                txtDate.setStyle("");
            }
            if (txtEndTime.getText().isEmpty()) {
                txtEndTime.setStyle("-fx-border-color: red;");
            } else {
                txtEndTime.setStyle("");
            }
            if (cmbEmpId.getValue() == null) {
                cmbEmpId.setStyle("-fx-border-color: red;");
            } else {
                cmbEmpId.setStyle("");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();

            String id = txtId.getText();
            LocalDate date = txtDate.getValue();
            Date datee = java.sql.Date.valueOf(date);
            String STime = txtStartTime.getText();
            String ETime = txtEndTime.getText();
            String eid = (String) cmbEmpId.getValue();

            Schedule schedule = new Schedule(id, (java.sql.Date) datee, STime, ETime, eid);

            try {
                boolean isSaved = ScheduleRepo.save(schedule);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "schedule saved!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        LocalDate date = txtDate.getValue();
        Date datee = java.sql.Date.valueOf(date);
        String STime=txtStartTime.getText();
        String ETime=txtEndTime.getText();
        String eid= (String) cmbEmpId.getValue();


        try {
            boolean isUpdated = ScheduleRepo.update(id, (java.sql.Date) datee,STime,ETime,eid);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "schedule updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = ScheduleRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "schedule deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void ScheduleIdOnAction(ActionEvent event) {
        String schId = txtId.getText();
        if (Regex.isScheduleId(schId)) {
            txtId.setStyle("-fx-border-color: green;");
            txtId.requestFocus();
        } else {
            txtId.setStyle("-fx-border-color: red;");
            txtId.requestFocus();
        }

    }
}

