package lk.ijse.eCounselling.Controller;

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
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Session;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.model.tm.SessionTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.SessionRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionFormController  {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

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
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadSessionTable() {
        ObservableList<SessionTm> SessionTMS = FXCollections.observableArrayList();


        for (Session session : sessionList) {
            SessionTm sessionTm = new SessionTm(
                    session.getId(),
                    session.getType(),
                    session.getDate(),
                    session.getDuration()
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
        String id = txtId.getText();
        String type = txtType.getText();
        LocalDate date = txtDate.getValue();
        Date datee = java.sql.Date.valueOf(date);
        int duration= Integer.parseInt(txtDuration.getText());
        Session session = new Session(id, type, (java.sql.Date) datee,duration);

        try {
            boolean isSaved = SessionRepo.save(session);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "session saved!").show();
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
        int duration= Integer.parseInt(txtDuration.getText());


        try {
            boolean isUpdated = SessionRepo.update(id,type,datee,duration);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "session updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    }

