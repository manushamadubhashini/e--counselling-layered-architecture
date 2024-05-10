package lk.ijse.eCounselling.Controller;

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
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Employee;
import lk.ijse.eCounselling.model.User;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.model.tm.EmployeeTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.EmployeeRepo;
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
    private TextField txtStatus;

    @FXML
    private TextField txtType;

    private List<Appointment> appointmentList = new ArrayList<>();
    public void initialize() {
        this.appointmentList = getAllAppointment();
        setCellValueFactory();
        loadAppointmentTable();
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
    }

    private void loadAppointmentTable() {
        ObservableList<AppointmentTm> AppointmentTMS = FXCollections.observableArrayList();


        for (Appointment appointment : appointmentList) {
            AppointmentTm appointmentTm=new AppointmentTm(
                    appointment.getId(),
                    appointment.getType(),
                    appointment.getDate(),
                    appointment.getStatus(),
                    appointment.getDuration()

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
        txtStatus.setText("");
        txtDuration.setText("");
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
        String id = txtId.getText();
        String type = txtType.getText();
        LocalDate date = txtDate.getValue();
        Date datee = java.sql.Date.valueOf(date);
        String status=txtStatus.getText();
        int duration= Integer.parseInt(txtDuration.getText());

        Appointment appointment = new Appointment(id, type, (java.sql.Date) datee, status,duration);

        try {
            boolean isSaved = AppointmentRepo.save(appointment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String type = txtType.getText();
        LocalDate date = txtDate.getValue();
        Date datee = java.sql.Date.valueOf(date);
        String status=txtStatus.getText();
        int duration= Integer.parseInt(txtDuration.getText());


        try {
            boolean isUpdated = AppointmentRepo.update(id,type, (java.sql.Date) datee,status,duration);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "appointment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
