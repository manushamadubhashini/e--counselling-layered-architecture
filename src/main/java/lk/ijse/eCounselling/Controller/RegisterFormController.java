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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.dto.User;
import lk.ijse.eCounselling.repository.UserRepo;
import javafx.scene.paint.Color;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterFormController {

    @FXML
    private JFXButton btnNewUser;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private Label lblPassword;


    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtConfirmPassword;


    @FXML
    private JFXComboBox<String> cmbUserType;

    @FXML
    private JFXButton btnRegiser;

    public void initialize() {
        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        cmbUserType.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnRegiser.setDisable(true);
        setType();

    }

    private void setType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> type = new ArrayList<>();
        type.add("Admin");
        type.add("Guidance counselor");
        type.add("Mental health counselor");
        type.add("Adoption counselor");
        type.add("Marriage counselor");
        type.add("Rehabilitation counselor");

        for (String TYPE : type) {
            obList.add(TYPE);
        }
        cmbUserType.setItems(obList);
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) {
        boolean hasError = false;
        StringBuilder errorMessage = new StringBuilder("Please fill in all fields correctly.\n");

        // Validate User ID
        if (txtUserId.getText().isEmpty() || !Regex.isValidUserId(txtUserId.getText())) {
            txtUserId.setStyle("-fx-border-color: red;");
            errorMessage.append("Invalid User ID.\n");
            hasError = true;
        } else {
            txtUserId.setStyle("");
        }

        // Validate User Name
        if (txtUserName.getText().isEmpty()) {
            txtUserName.setStyle("-fx-border-color: red;");
            errorMessage.append("User Name is required.\n");
            hasError = true;
        } else {
            txtUserName.setStyle("");
        }

        // Validate Password
        if (txtPassword.getText().isEmpty() || !isValidPassword(txtPassword.getText())) {
            txtPassword.setStyle("-fx-border-color: red;");
            errorMessage.append("Password must be at least 8 characters long and contain at least one special character (#, @, $).\n");
            hasError = true;
        } else {
            txtPassword.setStyle("");
        }

        // Validate Confirm Password
        if (txtConfirmPassword.getText().isEmpty() || !txtPassword.getText().equals(txtConfirmPassword.getText())) {
            txtConfirmPassword.setStyle("-fx-border-color: red;");
            errorMessage.append("Passwords do not match.\n");
            hasError = true;
        } else {
            txtConfirmPassword.setStyle("");
        }

        // Validate User Type
        if (cmbUserType.getValue() == null) {
            cmbUserType.setStyle("-fx-border-color: red;");
            errorMessage.append("User Type is required.\n");
            hasError = true;
        } else {
            cmbUserType.setStyle("");
        }

        // Show error alert if there are validation errors
        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage.toString());
            alert.show();
            return;
        }

        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String type = cmbUserType.getValue();
        String password = txtPassword.getText();

        User user = new User(id, name, type, password);

        try {
            boolean isSaved = UserRepo.setUser(user);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Registration Successfully!").show();
                init();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Register Form");
        stage.centerOnScreen();
    }

    @FXML
    public void txtPasswordOnAction(ActionEvent event) {
        String password = txtPassword.getText();
        if (!isValidPassword(password)) {
            txtPassword.setStyle("-fx-border-color: red;");
            lblPassword.setText("Password must be at least 8 characters long and contain at least one special character (#, @, $).");
            lblPassword.setTextFill(Color.RED);
            lblPassword.setStyle("-fx-font-size: 12;");
        } else {
            txtPassword.setStyle("-fx-border-color: green;");
            lblPassword.setText(" ");
            lblPassword.setStyle("-fx-font-size: 12;");
            txtConfirmPassword.requestFocus();
        }
    }

    @FXML
    public void txtUserIdOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        if (Regex.isValidUserId(userId)) {
            txtUserId.setStyle("-fx-border-color: green;");
            txtUserName.requestFocus();
        } else {
            txtUserId.setStyle("-fx-border-color: red;");
            txtUserId.requestFocus();
        }
    }

    @FXML
    public void txtNameOnAction(ActionEvent event) {
        txtUserName.setStyle("-fx-border-color: green");
        cmbUserType.requestFocus();
    }

    @FXML
    public void txtConfirmPasswordOnAction(ActionEvent event) {
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match!");
            alert.showAndWait();
        } else {
            txtConfirmPassword.setStyle("-fx-border-color: green");
            btnRegiser.requestFocus();
        }
    }

    @FXML
    public void userTypeOnAction(ActionEvent event) {
        cmbUserType.setStyle("-fx-border-color: green");
        txtPassword.requestFocus();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[#@$&].*");
    }

    @FXML
    void btnNewUserOnAction(ActionEvent event) {
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        cmbUserType.setValue("");
        txtUserId.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        cmbUserType.setDisable(false);
        txtConfirmPassword.setDisable(false);
        btnRegiser.setDisable(false);
        txtUserId.setText(generateId());
        txtUserId.setEditable(false);
        //init();

    }
    private String generateId(){
        try{
            return UserRepo.generateId();

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        return "U001";
    }
    private void init(){
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        cmbUserType.setValue("");
        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);
        cmbUserType.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnRegiser.setDisable(true);

    }

}