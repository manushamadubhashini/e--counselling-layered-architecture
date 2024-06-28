package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    public static boolean setUser(User user) throws SQLException {
        String sql = "INSERT INTO user VALUES(?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, user.getUserId());
        pstm.setObject(2, user.getUserName());
        pstm.setObject(3, user.getUserType());
        pstm.setObject(4, user.getPassword());
        return pstm.executeUpdate() > 0;

    }

    public static User setLogionDetail(String userName) throws SQLException {
        String sql = "SELECT user_id,user_name,user_type,password FROM user WHERE user_name = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userName);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getString("user_id"));
            user.setUserName(resultSet.getString("user_name"));
            user.setUserType(resultSet.getString("user_type"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } else {
            return null;
        }
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT user_id FROM user";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT user_id  FROM user ORDER BY user_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("user_id");
            int newAppointmentId = Integer.parseInt(id.replace("U", "")) + 1;
            return String.format("U%03d", newAppointmentId);
        } else {
            return "U001";
        }
    }





}







