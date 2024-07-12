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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.PatientDescBO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.PatientDescDTO;
import lk.ijse.eCounselling.dto.tm.PatientDescTm;
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

    PatientDescBO patientDescBO= (PatientDescBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PATIENTREPORTDESC);


    public void initialize() {
        setCellValueFactory();
        loadTreatmentTable();
    }


    private void loadTreatmentTable() {
        tblPatient.getItems().clear();
        try {
            ArrayList<PatientDescDTO> patientDescDTOS=patientDescBO.getAll();
            for (PatientDescDTO p:patientDescDTOS){
                tblPatient.getItems().add(new PatientDescTm(p.getId(),p.getRid(),p.getName(),p.getDob(),p.getAddress(),p.getContact(),p.getStatus(),p.getGender(),p.getDes()));
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
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
        stage.setTitle("PatientDTO Form");
        stage.centerOnScreen();

    }

    public void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/ReportDTO/PatientDTO.jrxml");
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

