package lk.ijse.eCounselling.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import lk.ijse.eCounselling.Util.Regex;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.User;
import lk.ijse.eCounselling.model.tm.AppointmentTm;
import lk.ijse.eCounselling.repository.AppointmentRepo;
import lk.ijse.eCounselling.repository.UserRepo;
import lombok.SneakyThrows;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.DoubleBinaryOperator;

public class RegisterFormController {
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
        // Check if any of the text fields are empty
        if (txtUserId.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty() || cmbUserType.getValue() == null) {
            // Set border color of empty text fields to red
            if (txtUserId.getText().isEmpty()) {
                txtUserId.setStyle("-fx-border-color: red;");
            } else {
                txtUserId.setStyle("");

            }if (txtUserName.getText().isEmpty()) {
                txtUserName.setStyle("-fx-border-color: red;");
            }else{
                txtUserName.setStyle("");
            }
            if (txtPassword.getText().isEmpty()) {
                txtPassword.setStyle("-fx-border-color: red;");
            }else{
                txtPassword.setStyle("");
            }
            if (txtConfirmPassword.getText().isEmpty()) {
                txtConfirmPassword.setStyle("-fx-border-color: red;");
            }else{
                txtConfirmPassword.setStyle("");
            }
            if (cmbUserType.getValue() == null) {
                cmbUserType.setStyle("-fx-border-color: red;");
            }else{
                cmbUserType.setStyle("");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");

            // Position the alert near the confirm password field
            alert.initOwner(rootNode.getScene().getWindow());
            alert.showAndWait();


                String id = txtUserId.getText();
                String name = txtUserName.getText();
                String type = cmbUserType.getValue();
                String password = txtPassword.getText();

                User user = new User(id, name, type, password);

                try {
                    boolean isSaved = UserRepo.setUser(user);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Registration Successfully!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
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




    public void txtPasswordOnAction(ActionEvent event) {
        String password = txtPassword.getText();

        if (password.length() < 8) {
            txtPassword.setStyle("-fx-border-color: red;");
            lblPassword.setText("Password must be at least 8 characters long.");
            lblPassword.setTextFill(Color.RED);
            lblPassword.setStyle("-fx-font-size: 12;");
        } else if (!password.matches(".*[#@$&].*")) {
            // Set border color to red if password does not contain #, @, or $
            txtPassword.setStyle("-fx-border-color: red;");
            lblPassword.setText("Your Password is not strong");
            lblPassword.setTextFill(Color.RED);
            lblPassword.setStyle("-fx-font-size: 12;");
        } else {
            // Set border color to green if password is valid
            txtPassword.setStyle("-fx-border-color: green;");
            lblPassword.setText(" ");
            lblPassword.setStyle("-fx-font-sizet: 12;");
            txtConfirmPassword.requestFocus();
        }
    }

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

    public void txtNameOnAction(ActionEvent event) {
        String name=txtUserName.getText();
        txtUserName.setStyle("-fx-border-color: green");
        cmbUserType.requestFocus();
    }

    public void txtConfirmPasswordOnAction(ActionEvent event) {
        // Perform registration process
        String passWord = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        if (!passWord.equals(confirmPassword)) {
            // Change border color of confirm password field to red
            txtConfirmPassword.setStyle("-fx-border-color: red;");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match!");

            // Position the alert near the confirm password field
            alert.initOwner(txtConfirmPassword.getScene().getWindow());
            alert.showAndWait();
        } else {
            // Reset border color of confirm password field
            txtConfirmPassword.setStyle("-fx-border-color: green");
            btnRegiser.requestFocus();

        }
    }

    public void userTypeOnAction(ActionEvent event) {
        String type=cmbUserType.getValue();
        cmbUserType.setStyle("-fx-border-color: green");
        txtPassword.requestFocus();
    }
}

