package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.*;
import lk.ijse.eCounselling.model.tm.*;
import lk.ijse.eCounselling.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreatmentFormController {

    @FXML
    public JFXComboBox cmbPatientId;

    @FXML
    private TableColumn<?, ?> ColStatus;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPid;

    @FXML
    private TableColumn<?, ?> colMid;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<TreatmentDescTm> tblTreatment;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private JFXComboBox cmbMid;

    @FXML
    private TextField txtStatus;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnNewTreatment;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    public void initialize() {
        setCellValueFactory();
        loadTreatmentTable();
        getPatientIds();
        getTreatmentMethodIds();
        txtId.setDisable(true);
        cmbMid.setDisable(true);
        txtStatus.setDisable(true);
        txtDescription.setDisable(true);
        txtDuration.setDisable(true);
        cmbPatientId.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        tblTreatment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            //btnUpdate.setText(newValue != null ? "Update" : "Save");
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                cmbMid.setValue(newValue.getMid());
                txtStatus.setText(newValue.getStatus());
                txtDescription.setText(newValue.getDescription());
                txtDuration.setText(String.valueOf(newValue.getDuration()));
                cmbPatientId.setValue(newValue.getPid());

                txtId.setDisable(false);
                cmbMid.setDisable(false);
                txtStatus.setDisable(false);
                txtDescription.setDisable(false);
                txtDuration.setDisable(false);
                cmbPatientId.setDisable(false);
            }
        });
    }

    private void getTreatmentMethodIds() {
        try {
            ArrayList<TreatmentMethod> treatmentMethods=TreatmentMethodRepo.getAll();
            for (TreatmentMethod tm:treatmentMethods){
                cmbMid.getItems().add(tm.getMid());
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getPatientIds() {
        try {
            ArrayList<Patient> patients=PatientRepo.getAll();
            for (Patient p:patients){
                cmbPatientId.getItems().add(p.getId());
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }




    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
    }

    private void loadTreatmentTable() {
        try {
            ArrayList<TreatmentDesc> treatmentDescs =TreatmentDescRepo.getAll();
            for (TreatmentDesc d:treatmentDescs){
                tblTreatment.getItems().add(new TreatmentDescTm(d.getId(),d.getMid(),d.getStatus(),d.getDescription(),d.getDuration(),d.getPid()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

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
        cmbMid.setValue(null);
        txtStatus.setText("");
        txtDescription.setText("");
        txtDuration.setText("");
        cmbPatientId.setValue(null);
        tblTreatment.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblTreatment.getSelectionModel().getSelectedItem().getId();
        String mid= tblTreatment.getSelectionModel().getSelectedItem().getMid();

        try {
            boolean isTreatmentDeleted = TreatmentRepo.delete(id);
            boolean isDetailDeleted=TreatmentMethodDetailRepo.delete(id,mid);
            if (isTreatmentDeleted || isDetailDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment and Detail deleted!").show();
                init();
            }
            tblTreatment.getItems().remove(tblTreatment.getSelectionModel().getSelectedItem());
            tblTreatment.getSelectionModel().clearSelection();
            txtId.clear();
            cmbMid.setValue(null);
            cmbPatientId.setValue(null);
            txtStatus.clear();
            txtDescription.clear();
            txtDuration.clear();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


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
            if (txtStatus.getText().isEmpty()) {
                txtStatus.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtStatus.setStyle("");

            }
            if (txtDuration.getText().isEmpty()) {
                txtDuration.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtDuration.setStyle("");
            }
            if (cmbMid.getValue()== null) {
                cmbMid.setStyle("-fx-border-color: red;");
            } else {
                cmbMid.setStyle("");
            }
            if (txtDescription.getText().isEmpty()) {
                txtDescription.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtDescription.setStyle("");
            }
            if(cmbPatientId.getValue()==null){
                cmbMid.setStyle("-fx-border-color: red");
                hasError=true;
            }else{
                cmbPatientId.setStyle("");
            }

            if(hasError) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.show();
                }
                String id = txtId.getText();
                String mid = (String) cmbMid.getValue();
                String pid = (String) cmbPatientId.getValue();
                String status = txtStatus.getText();
                String description=txtDescription.getText();
                int duration = Integer.parseInt(txtDuration.getText());

            Treatment treatment = new Treatment(id, status, pid);
            TreatmentMethodDetail treatmentMethodDetail = new TreatmentMethodDetail(id, mid, duration);

            try {
                boolean isTreatmentSaved = TreatmentRepo.save(treatment);
                boolean isDetailSaved = TreatmentMethodDetailRepo.save(treatmentMethodDetail);
                if (isTreatmentSaved  && isDetailSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Treatment, and Detail saved!").show();
                    init();
                }
                tblTreatment.getItems().add(new TreatmentDescTm(id,mid,status,description,duration,pid));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String mid = (String) cmbMid.getValue();
        String pid= (String) cmbPatientId.getValue();
        String status=txtStatus.getText();
        int duration= Integer.parseInt(txtDuration.getText());
        String description=txtDescription.getText();

        try {
            boolean isTreatmentUpdated = TreatmentRepo.update(id,status,pid);
            boolean isDetailUpdated=TreatmentMethodDetailRepo.update(id,mid,duration);

            if (isTreatmentUpdated  && isDetailUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment and Detail updated!").show();
                init();
            }
            TreatmentDescTm selectedItem=tblTreatment.getSelectionModel().getSelectedItem();
            selectedItem.setId(id);
            selectedItem.setStatus(status);
            selectedItem.setDescription(description);
            selectedItem.setDuration(duration);
            selectedItem.setPid(pid);
            tblTreatment.refresh();
            tblTreatment.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/Report/TreatmentRepo.jrxml");
        JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);
        String patientId = (String) cmbPatientId.getValue();

        Map<String, Object> data = new HashMap<>();
        data.put("PatientId",patientId);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        data,
                        DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);


    }
    @FXML
    void btnNewTreatmentOnAction(ActionEvent event) {
        txtId.clear();
        cmbMid.setValue(null);
        txtStatus.clear();
        txtDescription.clear();
        txtDuration.clear();
        cmbPatientId.setValue(null);
        txtId.setDisable(false);
        cmbMid.setDisable(false);
        txtStatus.setDisable(false);
        txtDescription.setDisable(false);
        txtDuration.setDisable(false);
        cmbPatientId.setDisable(false);
        txtId.setText(generateId());
        txtId.setEditable(false);
        btnSave.setDisable(false);
    }
    private String generateId(){
        try{
           return TreatmentRepo.generateId();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "T001";
    }
    private void init(){
        txtId.setDisable(true);
        cmbMid.setDisable(true);
        txtStatus.setDisable(true);
        txtDescription.setDisable(true);
        txtDuration.setDisable(true);
        cmbPatientId.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
}
