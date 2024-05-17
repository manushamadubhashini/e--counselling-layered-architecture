package lk.ijse.eCounselling.Controller;

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
    private TableColumn<?, ?> colMId;

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
    private TextField txtMethodId;

    @FXML
    private TextField txtStatus;

    private List<Treatment> treatmentList = new ArrayList<>();

    private List<TreatmentMethod> treatmentMethodList = new ArrayList<>();

    private List<TreatmentMethodDetail> treatmentMethodDetails = new ArrayList<>();

    private List<TreatmentDesc> treatmentDescsList = new ArrayList<>();


    public void initialize() {
        treatmentList= getAllTreatment();
        treatmentMethodList=getAllMethod();
        treatmentMethodDetails=getAllDetail();
        treatmentDescsList=getAllDesc();
        System.out.println("Treatment Desc List: " + treatmentDescsList);
        setCellValueFactory();
        loadTreatmentTable();
        getPatientIds();
    }

    private void getPatientIds() {
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

    private List<TreatmentDesc> getAllDesc() {

        List<TreatmentDesc>  treatmentDescsList1 = null;
        try {
            treatmentDescsList1 = TreatmentDescRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return treatmentDescsList1;
    }

    private List<TreatmentMethodDetail> getAllDetail() {
        List<TreatmentMethodDetail>  treatmentMethodDetailsList1 = null;
        try {
            treatmentMethodDetailsList1 = TreatmentMethodDetailRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return treatmentMethodDetailsList1;
    }



    private List<TreatmentMethod> getAllMethod() {
        List<TreatmentMethod>  treatmentMethodList1 = null;
        try {
            treatmentMethodList1 = TreatmentMethodRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return treatmentMethodList1;
    }

    private List<Treatment> getAllTreatment() {
        List<Treatment>  treatmentList1 = null;
        try {
            treatmentList1 = TreatmentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return treatmentList1;
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMId.setCellValueFactory(new PropertyValueFactory<>("mid"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadTreatmentTable() {
        ObservableList<TreatmentDescTm> TreatmentDescTMS = FXCollections.observableArrayList();


        for (TreatmentDesc treatmentDesc : treatmentDescsList) {
            TreatmentDescTm treatmentDescTm=new TreatmentDescTm(
                    treatmentDesc.getId(),
                    treatmentDesc.getMid(),
                    treatmentDesc.getStatus(),
                    treatmentDesc.getDescription(),
                    treatmentDesc.getDuration()

            );
            TreatmentDescTMS.add(treatmentDescTm);

        }
        tblTreatment.setItems(TreatmentDescTMS);
        TreatmentDescTm selectedItem = (TreatmentDescTm) tblTreatment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);


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
        cmbPatientId.setItems(null);
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
        String pid= (String) cmbPatientId.getValue();
        String status=txtStatus.getText();
        String description=txtDescription.getText();
        int duration= Integer.parseInt(txtDuration.getText());

        Treatment treatment = new Treatment(id,status,pid);
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
        String pid= (String) cmbPatientId.getValue();
        String status=txtStatus.getText();
        String description=txtDescription.getText();
        int duration= Integer.parseInt(txtDuration.getText());

        try {
            boolean isTreatmentUpdated = TreatmentRepo.update(id,status,pid);
            boolean isMethodUpdated=TreatmentMethodRepo.update(mid,description);
            boolean isDetailUpdated=TreatmentMethodDetailRepo.update(id,mid,duration);

            if (isTreatmentUpdated && isMethodUpdated && isDetailUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Treatment, Method, and Detail updated!").show();
            }
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
}
