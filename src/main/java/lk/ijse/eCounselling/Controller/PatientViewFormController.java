package lk.ijse.eCounselling.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.PatientDesc;
import lk.ijse.eCounselling.model.TreatmentDesc;
import lk.ijse.eCounselling.model.tm.PatientDescTm;
import lk.ijse.eCounselling.model.tm.TreatmentDescTm;
import lk.ijse.eCounselling.repository.PatientDescRepo;
import lk.ijse.eCounselling.repository.TreatmentDescRepo;
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

public class PatientViewFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colRid;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PatientDescTm> tblPatient;

    private List<PatientDesc> patientDescList = new ArrayList<>();

    public void initialize() {
        patientDescList = getAllDesc();
        setCellValueFactory();
        loadTreatmentTable();
    }

    private List<PatientDesc> getAllDesc() {
        List<PatientDesc> patientDescList1 = null;
        try {
            patientDescList1 = PatientDescRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientDescList1;
    }

    private void loadTreatmentTable() {
        ObservableList<PatientDescTm> PatientDescTMS = FXCollections.observableArrayList();


        for (PatientDesc patientDesc : patientDescList) {
            PatientDescTm patientDescTm = new PatientDescTm(
                    patientDesc.getId(),
                    patientDesc.getRid(),
                    patientDesc.getName(),
                    patientDesc.getDob(),
                    patientDesc.getAddress(),
                    patientDesc.getContact(),
                    patientDesc.getStatus(),
                    patientDesc.getGender(),
                    patientDesc.getDes()

            );
            PatientDescTMS.add(patientDescTm);

        }
        tblPatient.setItems(PatientDescTMS);
        PatientDescTm selectedItem = (PatientDescTm) tblPatient.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRid.setCellValueFactory(new PropertyValueFactory<>("rid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("des"));


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
    void btnPatientAddOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/patient_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Patient Form");
        stage.centerOnScreen();

    }

    public void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/Patient.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    data,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("JRException occurred: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException occurred: " + e.getMessage());
        }
    }
}

