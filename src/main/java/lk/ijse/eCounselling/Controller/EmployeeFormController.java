package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.util.Callback;
import lk.ijse.eCounselling.bo.BOFactory;
import lk.ijse.eCounselling.bo.custom.EmployeeBO;
import lk.ijse.eCounselling.bo.custom.UserBO;
import lk.ijse.eCounselling.bo.impl.EmployeeBOImpl;
import lk.ijse.eCounselling.dao.custom.EmployeeDAO;
import lk.ijse.eCounselling.dao.impl.EmployeeDAOImpl;
import lk.ijse.eCounselling.dto.EmployeeDTO;
import lk.ijse.eCounselling.dto.tm.EmployeeTm;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnNewEmployee;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJoinDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colUId;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDOB;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
   private TextField txtPosition;

    @FXML
    private JFXComboBox txtUserId;

    EmployeeBO employeeBO= (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);
    UserBO userBO=(UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);
    public void initialize() {
        txtId.setStyle("");
        txtName.setStyle("");
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtDOB.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtPosition.setDisable(true);
        txtDate.setDisable(true);
        txtUserId.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtId.setEditable(false);
        txtUserId.setDisable(false);
        setCellValueFactory();
        loadEmployeeTable();
        setUserId();
        txtDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Optional styling for disabled dates
                        }
                    }
                };
            }
        });
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            //btnUpdate.setText(newValue != null ? "Update" : "Save");
            btnUpdate.setDisable(newValue == null);

            if (newValue != null) {
                txtId.setText(newValue.getId());
                txtName.setText(newValue.getName());
                txtDOB.setText(newValue.getDOB());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                txtPosition.setText(newValue.getPosition());
                txtDate.setValue(newValue.getJoinDate());
                txtUserId.setValue(newValue.getUid());

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtDOB.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
                txtPosition.setDisable(false);
                txtDate.setDisable(false);
                txtUserId.setDisable(false);
            }
        });

    }
    private void setUserId(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = userBO.getIds();
            for (String code : codeList) {
                obList.add(code);
            }

            txtUserId.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadEmployeeTable() {
        tblEmployee.getItems().clear();
        try {
            ArrayList<EmployeeDTO> employees=employeeBO.getAll();
            for (EmployeeDTO e:employees){
                tblEmployee.getItems().add(new EmployeeTm(e.getId(),e.getName(),e.getDOB(),e.getAddress(),e.getContact(),e.getPosition(),e.getJoinDate(),e.getUid()));
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        colUId.setCellValueFactory(new PropertyValueFactory<>("uid"));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
            boolean hasError = false;
            // Set border color of empty text fields to red
            if (txtId.getText().isEmpty()) {
                txtId.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtId.setStyle("");
            }
            if (txtName.getText().isEmpty()) {
                txtName.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtName.setStyle("");
            }
            if (txtDOB.getText().isEmpty()) {
                txtDOB.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtDOB.setStyle("");
            }
            if (txtAddress.getText().isEmpty()) {
                txtAddress.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtAddress.setStyle("");
            }

            if (txtContact.getText().isEmpty()) {
                txtContact.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtContact.setStyle("");

            }
            if (txtPosition.getText().isEmpty()) {
                txtPosition.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtPosition.setStyle("");
            }
            if (txtDate.getValue() == null) {
                txtDate.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtDate.setStyle("");
            }
            if (txtUserId.getValue() == null) {
                txtUserId.setStyle("-fx-border-color: red;");
                hasError = true;
            } else {
                txtUserId.setStyle("");

            }

            if(hasError) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.show();
                return;
            }

            String id = txtId.getText();
            String name = txtName.getText();
            String dob = txtDOB.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            String position = txtPosition.getText();
            LocalDate joinDate = txtDate.getValue();
            String uid = (String) txtUserId.getValue();
            //String userId = getCurrentUserId();
            if(! name.matches("[A-Za-z ]+")){
                new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
                txtName.requestFocus();
                txtName.setStyle("-fx-border-color: red");
                return;
            }
            if(!dob.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")){
                new Alert(Alert.AlertType.ERROR,"Invalid Date of Birth").show();
                txtDOB.requestFocus();
                txtDOB.setStyle("-fx-border-color: red");
                return;
            }
            if (!address.matches(".{3,}")) {
                new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
                txtAddress.requestFocus();
                txtAddress.setStyle("-fx-border-color: red");
                return;
            }
            if(!contact.matches("^0[0-9]{9}$")){
                new Alert(Alert.AlertType.ERROR,"Invalid Phone Number").show();
                txtContact.requestFocus();
                txtContact.setStyle("-fx-border-color: red");
                return;
            }
            if(! position.matches("[A-Za-z ]+")){
                new Alert(Alert.AlertType.ERROR,"Invalid Value").show();
                txtPosition.requestFocus();
                txtPosition.setStyle("-fx-border-color: red");
                return;
            }


            EmployeeDTO employee = new EmployeeDTO(id, name, dob, address, contact, position, joinDate, uid);

            try {
                boolean isSaved = employeeBO.save(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                }
                tblEmployee.getItems().add(new EmployeeTm(id,name,dob,address,contact,position,joinDate,uid));
                init();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


    }


    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDOB.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtDate.setValue(null);
        txtUserId.setValue(null);
        tblEmployee.getSelectionModel().clearSelection();
        init();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String DOB=txtDOB.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position=txtPosition.getText();
        LocalDate joinDate=txtDate.getValue();
        String uid= (String) txtUserId.getValue();

        if(! name.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR,"Invalid Name").show();
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color: red");
            return;
        }
        if(!DOB.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Date of Birth").show();
            txtDOB.requestFocus();
            txtDOB.setStyle("-fx-border-color: red");
            return;
        }
        if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color: red");
            return;
        }
        if(!contact.matches("^0[0-9]{9}$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Phone Number").show();
            txtContact.requestFocus();
            txtContact.setStyle("-fx-border-color: red");
            return;
        }
        if(! position.matches("[A-Za-z ]+")){
            new Alert(Alert.AlertType.ERROR,"Invalid Value").show();
            txtPosition.requestFocus();
            txtPosition.setStyle("-fx-border-color: red");
            return;
        }



        EmployeeDTO employee = new EmployeeDTO(id, name,DOB, address, contact,position,joinDate,uid);
        try {
            boolean isUpdated = employeeBO.update(employee);
           if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
           EmployeeTm selectedEmployee=tblEmployee.getSelectionModel().getSelectedItem();
           selectedEmployee.setName(name);
           selectedEmployee.setDOB(DOB);
           selectedEmployee.setAddress(address);
           selectedEmployee.setContact(contact);
           selectedEmployee.setPosition(position);
           selectedEmployee.setJoinDate(joinDate);
           selectedEmployee.setUid(uid);
           tblEmployee.refresh();
           tblEmployee.getSelectionModel().clearSelection();
           init();


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = employeeBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
                tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
                tblEmployee.getSelectionModel().clearSelection();
                txtId.clear();
                txtName.clear();
                txtDOB.clear();
                txtAddress.clear();
                txtContact.clear();
                txtPosition.clear();
                txtDate.setValue(null);
                txtUserId.setValue(null);
                init();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
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
    void btnNewEmployeeOnAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtDOB.clear();
        txtAddress.clear();
        txtContact.clear();
        txtPosition.clear();
        txtDate.setValue(null);
        txtUserId.setValue(null);
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtDOB.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtPosition.setDisable(false);
        txtDate.setDisable(false);
        txtUserId.setDisable(false);
        btnSave.setDisable(false);
        txtId.setText(generateNewId());
        txtId.setEditable(false);

    }
    private String generateNewId() {
        try {
            return employeeBO.generateId();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "E001";
    }
    private void init(){
        txtId.setDisable(true);
        txtName.setDisable(true);
        txtDOB.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        txtPosition.setDisable(true);
        txtDate.setDisable(true);
        txtUserId.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

}










