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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnNewSchedule;

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

    public void initialize() {
        setCellValueFactory();
        loadScheduleTable();
        getEmployeeId();
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.setEditable(false);
        txtId.setDisable(true);
        txtDate.setDisable(true);
        txtStartTime.setDisable(true);
        txtEndTime.setDisable(true);
        cmbEmpId.setDisable(true);
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
        tblSchedule.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            //btnUpdate.setText(newValue != null ? "Update" : "Save");
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtDate.setValue(newValue.getDate());
                txtStartTime.setText(newValue.getStime());
                txtEndTime.setText(newValue.getEtime());
                cmbEmpId.setValue(newValue.getEid());

                txtId.setDisable(false);
                txtDate.setDisable(false);
                txtStartTime.setDisable(false);
                txtEndTime.setDisable(false);
                cmbEmpId.setDisable(false);
            }
        });
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
        tblSchedule.getItems().clear();
        try {
            ArrayList<Schedule> schedules = ScheduleRepo.getAll();
            for (Schedule s : schedules) {
                tblSchedule.getItems().add(new ScheduleTm(s.getId(), s.getDate(), s.getStime(), s.getEtime(), s.getEid()));
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStime.setCellValueFactory(new PropertyValueFactory<>("stime"));
        colEtime.setCellValueFactory(new PropertyValueFactory<>("etime"));
        coleid.setCellValueFactory(new PropertyValueFactory<>("eid"));

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
        tblSchedule.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
            boolean hasError=false;
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtId.setStyle("");
            }
            if (txtStartTime.getText().isEmpty()) {
                txtStartTime.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtStartTime.setStyle("");
            }
            if (txtDate.getValue() == null) {
                txtDate.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtDate.setStyle("");
            }
            if (txtEndTime.getText().isEmpty()) {
                txtEndTime.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtEndTime.setStyle("");
            }
            if (cmbEmpId.getValue() == null) {
                cmbEmpId.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                cmbEmpId.setStyle("");
            }
            if(hasError) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.show();
                return;
            }

            String id = txtId.getText();
            LocalDate date = txtDate.getValue();
            String STime = txtStartTime.getText();
            String ETime = txtEndTime.getText();
            String eid = (String) cmbEmpId.getValue();

            if(! STime.matches("\\b(1[0-2]|0?[1-9]):([0-5][0-9])\\s*(AM|PM|am|pm)\\b")){
                new Alert(Alert.AlertType.ERROR,"invalid time").show();
                txtStartTime.requestFocus();
                txtStartTime.setStyle("-fx-border-color: red");
           }
           if(! ETime.matches("\\b(1[0-2]|0?[1-9]):([0-5][0-9])\\s*(AM|PM|am|pm)\\b")) {
               new Alert(Alert.AlertType.ERROR,"invalid time").show();
               txtEndTime.requestFocus();
               txtEndTime.setStyle("-fx-border-color: red");
               return;
           }


            Schedule schedule = new Schedule(id, date, STime, ETime, eid);

            try {
                boolean isSaved = ScheduleRepo.save(schedule);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "schedule saved!").show();
                    init();
                }
                tblSchedule.getItems().add(new ScheduleTm(id,date,STime,ETime,eid));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtId.getText();
        LocalDate date = txtDate.getValue();
        String STime=txtStartTime.getText();
        String ETime=txtEndTime.getText();
        String eid= (String) cmbEmpId.getValue();


        try {
            boolean isUpdated = ScheduleRepo.update(id,date,STime,ETime,eid);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "schedule updated!").show();
            }
            ScheduleTm selectedItem=tblSchedule.getSelectionModel().getSelectedItem();
            selectedItem.setDate(date);
            selectedItem.setStime(STime);
            selectedItem.setEtime(ETime);
            selectedItem.setEid(eid);
            tblSchedule.refresh();
            tblSchedule.getSelectionModel().clearSelection();
            init();
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
            tblSchedule.getItems().remove(tblSchedule.getSelectionModel().getSelectedItem());
            tblSchedule.getSelectionModel().clearSelection();
            txtId.clear();
            txtDate.setValue(null);
            txtStartTime.clear();
            txtEndTime.clear();
            cmbEmpId.setValue(null);
            init();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewScheduleOnAction(ActionEvent event) {
        txtId.clear();
        txtDate.setValue(null);
        txtStartTime.clear();
        txtEndTime.clear();
        cmbEmpId.setValue(null);
        txtId.setDisable(false);
        txtDate.setDisable(false);
        txtStartTime.setDisable(false);
        txtEndTime.setDisable(false);
        cmbEmpId.setDisable(false);
        txtId.setText(generateNewId());
        btnSave.setDisable(false);
    }
    private String generateNewId(){
        try {
            //Generate New ID
            return ScheduleRepo.generateId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        }
        return "H001";
    }
    private void init(){
        txtId.setDisable(true);
        txtDate.setDisable(true);
        txtStartTime.setDisable(true);
        txtEndTime.setDisable(true);
        cmbEmpId.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}

