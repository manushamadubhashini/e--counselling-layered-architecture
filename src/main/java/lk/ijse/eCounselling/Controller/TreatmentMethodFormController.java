package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.eCounselling.dto.TreatmentMethod;
import lk.ijse.eCounselling.dto.tm.TreatmentMethodTm;
import lk.ijse.eCounselling.repository.TreatmentMethodRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentMethodFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnNewMrthod;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<TreatmentMethodTm> tblTreatmentMethod;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    public void initialize(){
        txtId.setDisable(true);
        txtDescription.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        setCellValueFactory();
        loadTreatmentMethodTable();
        tblTreatmentMethod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            //btnUpdate.setText(newValue != null ? "Update" : "Save");
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getMid());
                txtDescription.setText(newValue.getDescription());

                txtId.setDisable(false);
                txtDescription.setDisable(false);
            }
        });
    }

    private void loadTreatmentMethodTable() {
        tblTreatmentMethod.getItems().clear();
        try {
            ArrayList<TreatmentMethod> treatmentMethods= TreatmentMethodRepo.getAll();
            for (TreatmentMethod tm:treatmentMethods){
                tblTreatmentMethod.getItems().add(new TreatmentMethodTm(tm.getMid(),tm.getDescription()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane= FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage=(Stage) root.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();

    }
    private void clearField() {
        txtId.setText("");
        txtDescription.setText("");
        tblTreatmentMethod.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id=tblTreatmentMethod.getSelectionModel().getSelectedItem().getMid();
        try {
            boolean isDeleted=TreatmentMethodRepo.delete(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Treatment Method Deleted").show();
                init();
            }
            tblTreatmentMethod.getItems().remove(tblTreatmentMethod.getSelectionModel().getSelectedItem());
            txtId.clear();
            txtDescription.clear();
            tblTreatmentMethod.getSelectionModel().clearSelection();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnNewMethodOnAction(ActionEvent event) {
        txtId.clear();
        txtDescription.clear();
        txtId.setDisable(false);
        txtDescription.setDisable(false);
        btnSave.setDisable(false);
        txtId.setText(generateNewId());
        txtId.setEditable(false);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean hasError=false;
        if (txtId.getText().isEmpty()){
            txtId.setStyle("-fx-border-color: red ");
            hasError=true;

        }else{
            txtId.setStyle("");
        }
        if (txtDescription.getText().isEmpty()){
            txtDescription.setStyle("-fx-border-color: red");
            hasError=true;
        }else{
            txtDescription.setStyle("");
        }
        if (hasError){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        String id=txtId.getText();
        String description=txtDescription.getText();

        if(! description.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR,"invalid value").show();
            txtDescription.setStyle("-fx-border-color: red");
            txtDescription.requestFocus();
            return;
        }

        TreatmentMethod treatmentMethod=new TreatmentMethod(id,description);
        try {
            boolean isSaved=TreatmentMethodRepo.save(treatmentMethod);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"treatment method saved").show();
                init();
            }
            tblTreatmentMethod.getItems().add(new TreatmentMethodTm(id,description));
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String description = txtDescription.getText();
        try {
            boolean isUpdated = TreatmentMethodRepo.update(id, description);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "treatment method updated").show();
                init();
            }
            TreatmentMethodTm selectedItem = tblTreatmentMethod.getSelectionModel().getSelectedItem();
            selectedItem.setMid(id);
            selectedItem.setDescription(description);
            tblTreatmentMethod.refresh();
            tblTreatmentMethod.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void init(){
        txtId.setDisable(true);
        txtDescription.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSave.setDisable(true);
    }

    private String generateNewId(){
        try {
            return TreatmentMethodRepo.generateId();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "M001";
    }

}
