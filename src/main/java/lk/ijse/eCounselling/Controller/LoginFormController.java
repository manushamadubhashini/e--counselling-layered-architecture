package lk.ijse.eCounselling.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.User;
import lk.ijse.eCounselling.repository.UserRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        User user= UserRepo.setLogionDetail(userName);
        try {
            if (user.getPassword().equals(password)) {
                navigateDashBoard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect").show();
            }
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "User name not found").show();


        }
    }

    private void navigateDashBoard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

    }

    public void linkRegistrationOnAction(ActionEvent event) throws IOException {
        Parent rootNode=FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));
        Scene scene=new Scene(rootNode);
        Stage stage= (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("registration form");
        stage.show();

    }
}