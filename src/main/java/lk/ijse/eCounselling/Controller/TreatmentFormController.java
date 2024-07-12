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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.PatientBO;
import lk.ijse.eCounselling.bo.custom.TreatmentBO;
import lk.ijse.eCounselling.bo.custom.TreatmentDescBO;
import lk.ijse.eCounselling.bo.custom.TreatmentMethodBO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.*;
import lk.ijse.eCounselling.dto.tm.*;
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
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private JFXComboBox cmbMid;

    @FXML
    private JFXComboBox cmbStatus;

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

    TreatmentBO treatmentBO=(TreatmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TREATMENT);
    TreatmentMethodBO treatmentMethodBO=(TreatmentMethodBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TREATMENTMETHOD);

    TreatmentDescBO treatmentDescBO=(TreatmentDescBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.TREATMENTDESC);

    PatientBO patientBO=(PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PATIENT);

    public void initialize() {
        setCellValueFactory();
        loadTreatmentTable();
        getPatientIds();
        getTreatmentMethodIds();
        setStatus();
        txtId.setDisable(true);
        cmbMid.setDisable(true);
        cmbStatus.setDisable(true);
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
                cmbStatus.setValue(newValue.getStatus());
                txtDuration.setText(String.valueOf(newValue.getDuration()));
                cmbPatientId.setValue(newValue.getPid());

                txtId.setDisable(false);
                cmbMid.setDisable(false);
                cmbStatus.setDisable(false);
                txtDuration.setDisable(false);
                cmbPatientId.setDisable(false);
            }
        });
    }

    private void setStatus(){
        ObservableList<String> observableList=FXCollections.observableArrayList();
        List<String> arrayList=new ArrayList<>();
        arrayList.add("Completed");
        arrayList.add("Pending");
        arrayList.add("In Progress");
        arrayList.add("Cancelled");
        for (String status:arrayList){
            observableList.add(status);
        }
        cmbStatus.setItems(observableList);
    }

    private void getTreatmentMethodIds() {
        try {
            ArrayList<TreatmentMethodDTO> treatmentMethods=treatmentMethodBO.getAll();
            for (TreatmentMethodDTO tm:treatmentMethods){
                cmbMid.getItems().add(tm.getMid());
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getPatientIds() {
        try {
            ArrayList<PatientDTO> patientDTOS=patientBO.getAll();
            for (PatientDTO p:patientDTOS){
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
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
    }

    private void loadTreatmentTable() {
        try {
            ArrayList<TreatmentDescDTO> treatmentDescs =treatmentDescBO.getAll();
            for (TreatmentDescDTO d:treatmentDescs){
                tblTreatment.getItems().add(new TreatmentDescTm(d.getId(),d.getMid(),d.getStatus(),d.getDuration(),d.getPid()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
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
        cmbMid.setValue(null);
        cmbStatus.setValue(null);
        txtDuration.setText("");
        cmbPatientId.setValue(null);
        tblTreatment.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    boolean btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id =txtId.getText();
        boolean isTreatmentDeleted=treatmentBO.TreatmentDelete(id);
        if (isTreatmentDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "TreatmentDTO and Detail deleted!").show();
            init();
            tblTreatment.getItems().remove(tblTreatment.getSelectionModel().getSelectedItem());
            tblTreatment.getSelectionModel().clearSelection();
            txtId.clear();
            cmbMid.setValue(null);
            cmbPatientId.setValue(null);
            cmbStatus.setValue(null);
            txtDuration.clear();
            return true;
        }
        return false;


    }

    @FXML
    boolean btnSaveOnAction(ActionEvent event) throws SQLException {
            boolean hasError=false;
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                txtId.setStyle("");
            }
            if (cmbStatus.getItems()==null) {
                cmbStatus.setStyle("-fx-border-color: red;");
                hasError=true;
            } else {
                cmbStatus.setStyle("");

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
                String status = (String) cmbStatus.getValue();
                int duration= Integer.parseInt(txtDuration.getText());
                List<TreatmentMethodDetailDTO> treatmentMethodDetailDTOList=new ArrayList<>();
                treatmentMethodDetailDTOList.add(new TreatmentMethodDetailDTO(id,mid,duration));
             try {
                 duration = Integer.parseInt(txtDuration.getText());
             }catch (NumberFormatException e){
                  txtDuration.setStyle("-fx-border-color: red");
                  txtDuration.requestFocus();
                  //return;
             }

            if(! txtDuration.getText().matches("^(?:[1-9][0-9]?|100)$")){
                 new Alert(Alert.AlertType.ERROR,"invalid value").show();
                 txtDuration.setStyle("-fx-border-color: red");
                 txtDuration.requestFocus();
             }
            boolean isTreatmentSaved=treatmentBO.TreatmentSave(new TreatmentDTO(id,status,pid,treatmentMethodDetailDTOList));
            if (isTreatmentSaved==true) {
                    new Alert(Alert.AlertType.CONFIRMATION, "TreatmentDTO, and Detail saved!").show();
                    init();
                    tblTreatment.getItems().add(new TreatmentDescTm(id,mid,status,duration,pid));
            }
            return false;
    }

    @FXML
    boolean btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        String mid = (String) cmbMid.getValue();
        String pid= (String) cmbPatientId.getValue();
        String status= (String) cmbStatus.getValue();
        int duration= Integer.parseInt(txtDuration.getText());
        List<TreatmentMethodDetailDTO> treatmentMethodDetailDTOList=new ArrayList<>();
        treatmentMethodDetailDTOList.add(new TreatmentMethodDetailDTO(id,mid,duration));

        try {
            duration = Integer.parseInt(txtDuration.getText());
        }catch (NumberFormatException e){
            txtDuration.setStyle("-fx-border-color: red");
            txtDuration.requestFocus();
            //return;
        }

        if(! txtDuration.getText().matches("^(?:[1-9][0-9]?|100)$")){
            new Alert(Alert.AlertType.ERROR,"invalid value").show();
            txtDuration.setStyle("-fx-border-color: red");
            txtDuration.requestFocus();
        }
        boolean isTreatmentUpdated=treatmentBO.TreatmentUpdate(new TreatmentDTO(id,status,pid,treatmentMethodDetailDTOList));
            if (isTreatmentUpdated== true) {
                new Alert(Alert.AlertType.CONFIRMATION, "TreatmentDTO and Detail updated!").show();
                init();
                TreatmentDescTm selectedItem=tblTreatment.getSelectionModel().getSelectedItem();
                selectedItem.setId(id);
                selectedItem.setStatus(status);
                selectedItem.setDuration(duration);
                selectedItem.setPid(pid);
                tblTreatment.refresh();
                tblTreatment.getSelectionModel().clearSelection();
            }
            return false;
    }


    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign =
                JRXmlLoader.load("src/main/resources/ReportDTO/TreatmentRepo.jrxml");
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
        cmbStatus.setValue(null);
        txtDuration.clear();
        cmbPatientId.setValue(null);
        txtId.setDisable(false);
        cmbMid.setDisable(false);
        cmbStatus.setDisable(false);
        txtDuration.setDisable(false);
        cmbPatientId.setDisable(false);
        txtId.setText(generateId());
        txtId.setEditable(false);
        btnSave.setDisable(false);
    }
    private String generateId(){
        try{
            return treatmentBO.generateId();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "T001";
    }
    private void init(){
        txtId.setDisable(true);
        cmbMid.setDisable(true);
        cmbStatus.setDisable(true);
        txtDuration.setDisable(true);
        cmbPatientId.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
}
