package lk.ijse.eCounselling.Controller;

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
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Session;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.model.tm.SessionTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.EmployeeRepo;
import lk.ijse.eCounselling.repository.PatientRepo;
import lk.ijse.eCounselling.repository.SessionRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionFormController  {

    @FXML
    private JFXComboBox cmbId;

    @FXML
    private JFXComboBox cmbPaid;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPaId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SessionTm> tblSession;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtType;

    private List<Session> sessionList = new ArrayList<>();
    public void initialize() {
        this.sessionList = getAllSession();
        setCellValueFactory();
        loadSessionTable();
        getEmployeeId();
        getPatientId();
    }

    private void getPatientId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = PatientRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbPaid.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void getEmployeeId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = EmployeeRepo.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            cmbId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colEid.setCellValueFactory(new PropertyValueFactory<>("eid"));
        colPaId.setCellValueFactory(new PropertyValueFactory<>("pid"));
    }

    private void loadSessionTable() {
        ObservableList<SessionTm> SessionTMS = FXCollections.observableArrayList();


        for (Session session : sessionList) {
            SessionTm sessionTm = new SessionTm(
                    session.getId(),
                    session.getType(),
                    session.getDate(),
                    session.getDuration(),
                    session.getEid(),
                    session.getPid()
            );
            SessionTMS.add(sessionTm);

        }
        tblSession.setItems(SessionTMS);
        SessionTm selectedItem = (SessionTm) tblSession.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);

    }

    private List<Session> getAllSession() {
        List<Session>  sessionList1 = null;
        try {
            sessionList1 = SessionRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessionList1;

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
        txtDuration.setText("");
        cmbId.setValue(null);
        cmbPaid.setValue(null);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = SessionRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "session deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

}

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || txtType.getText().isEmpty() || txtDate.getValue() == null || txtDuration.getText().isEmpty() || cmbId.getValue() == null || cmbPaid.getValue() == null) {
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
            if (cmbId.getValue() == null) {
                cmbId.setStyle("-fx-border-color: red;");
            } else {
                cmbId.setStyle("");
            }
            if (cmbPaid.getValue() == null) {
                cmbPaid.setStyle("-fx-border-color: red;");
            } else {
                cmbPaid.setStyle("");
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
            int duration = Integer.parseInt(txtDuration.getText());
            String eid = (String) cmbId.getValue();
            String pid = (String) cmbPaid.getValue();
            Session session = new Session(id, type, (java.sql.Date) datee, duration, eid, pid);

            try {
                boolean isSaved = SessionRepo.save(session);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "session saved!").show();
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
        int duration= Integer.parseInt(txtDuration.getText());
        String eid= (String) cmbId.getValue();
        String pid= (String) cmbPaid.getValue();


        try {
            boolean isUpdated = SessionRepo.update(id,type,datee,duration,eid,pid);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "session updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void SessionIdOnAction(ActionEvent event) {
        String sesId = txtId.getText();
        if (Regex.isSessionId(sesId)) {
            txtId.setStyle("-fx-border-color: green;");
            txtId.requestFocus();
        } else {
            txtId.setStyle("-fx-border-color: red;");
            txtId.requestFocus();
        }

    }
}

