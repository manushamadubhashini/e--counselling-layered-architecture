package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public static boolean setUser(User user) throws SQLException {
        String sql="INSERT INTO user VALUES(?,?,?,?)";
        PreparedStatement pstm=DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,user.getUserId());
        pstm.setObject(2,user.getUserName());
        pstm.setObject(3,user.getUserType());
        pstm.setObject(4,user.getPassword());
        return pstm.executeUpdate() > 0;

    }

   public static User setLogionDetail(String userName) throws SQLException {
       String sql = "SELECT user_id, user_name, user_type, password FROM user WHERE user_name = ?";
       try (Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)) {
           pstm.setString(1, userName);
           try (ResultSet resultSet = pstm.executeQuery()) {
               if (resultSet.next()) {
                   User user = new User();
                   user.setUserId(resultSet.getString("user_id"));
                   user.setUserName(resultSet.getString("user_name"));
                   user.setUserType(resultSet.getString("user_type"));
                   user.setPassword(resultSet.getString("password"));
                   return user;
               }
           }
       }
       return null; // Return null if user not found
   }


    }

