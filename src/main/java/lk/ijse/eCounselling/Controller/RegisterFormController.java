package lk.ijse.eCounselling.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.eCounselling.db.DbConnection;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.DoubleBinaryOperator;

public class RegisterFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserType;

     @FXML
     void btnRegistrationOnAction(ActionEvent event) {
        String user_id = txtUserId.getText();
        String user_name = txtUserName.getText();
        String  user_type  = txtUserType.getText();
        String  password   = txtPassword.getText();

        saveUser(user_id,user_name,user_type,password);


    }

    private void saveUser(String user_id, String user_name , String user_type , String password) {
        try {
            String sql="INSERT INTO user VALUES(?,?,?,?)";
            Connection connection=DbConnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setObject(1,user_id);
            pstm.setObject(2,user_name);
            pstm.setObject(3,user_type);
            pstm.setObject(4,password);

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"user saved!").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();

    }


}
