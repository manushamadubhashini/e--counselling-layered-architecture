package lk.ijse.eCounselling.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Treatment;
import lk.ijse.eCounselling.model.TreatmentMethod;
import lk.ijse.eCounselling.model.TreatmentMethodDetail;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.repository.TreatmentMethodDetailRepo;
import lk.ijse.eCounselling.repository.TreatmentMethodRepo;
import lk.ijse.eCounselling.repository.TreatmentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentFormController {

    @FXML
    private TableColumn<?, ?> ColStatus;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblTreatment;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMethodId;

    @FXML
    private TextField txtStatus;

    private List<Treatment> treatmentList = new ArrayList<>();

    private List<TreatmentMethod> treatmentMethodList = new ArrayList<>();

    private List<TreatmentMethodDetail> treatmentMethodDetails = new ArrayList<>();
    public void initialize() {
        treatmentList= getAllTreatment();
        treatmentMethodList=getAllMethod();
        treatmentMethodDetails=getAllDetail();
        setCellValueFactory();
        loadTreatmentTable();
    }

    private List<TreatmentMethodDetail> getAllDetail() {
        return null;
    }

    private List<TreatmentMethod> getAllMethod() {
        return null;
    }

    private List<Treatment> getAllTreatment() {
        return null;
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMId.setCellValueFactory(new PropertyValueFactory<>("mid"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadTreatmentTable() {



    }


    @FXML
    void btnBackOnAction(MouseEvent event) throws IOException {
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
        txtMethodId.setText("");
        txtStatus.setText("");
        txtDescription.setText("");
        txtDuration.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        String mid=txtMethodId.getText();

        try {
            boolean isTreatmentDeleted = TreatmentRepo.delete(id);
            boolean isMethodDeleted=TreatmentMethodRepo.delete(mid);
            boolean isDetailDeleted=TreatmentMethodDetailRepo.delete(id,mid);
            if (isTreatmentDeleted && isMethodDeleted && isDetailDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment, Method, and Detail deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String mid = txtMethodId.getText();
        String status=txtStatus.getText();
        String description=txtDescription.getText();
        int duration= Integer.parseInt(txtDuration.getText());

        Treatment treatment = new Treatment(id,status);
        TreatmentMethod treatmentMethod=new TreatmentMethod(mid,description);
        TreatmentMethodDetail treatmentMethodDetail=new TreatmentMethodDetail(id,mid,duration);

        try {
            boolean isTreatmentSaved = TreatmentRepo.save(treatment);
            boolean isMethodSaved= TreatmentMethodRepo.save(treatmentMethod);
            boolean isDetailSaved= TreatmentMethodDetailRepo.save(treatmentMethodDetail);
            if (isTreatmentSaved && isMethodSaved && isDetailSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment, Method, and Detail saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String mid = txtMethodId.getText();
        String status=txtStatus.getText();
        String description=txtDescription.getText();
        int duration= Integer.parseInt(txtDuration.getText());

        try {
            boolean isTreatmentUpdated = TreatmentRepo.update(id,status);
            boolean isMethodUpdated=TreatmentMethodRepo.update(mid,description);
            boolean isDetailUpdated=TreatmentMethodDetailRepo.update(id,mid,duration);

            if (isTreatmentUpdated && isMethodUpdated && isDetailUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment, Method, and Detail updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

}
