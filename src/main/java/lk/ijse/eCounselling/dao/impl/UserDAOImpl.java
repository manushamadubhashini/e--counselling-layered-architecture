package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.UserDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.UserDTO;
import lk.ijse.eCounselling.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
    @Override
    public  boolean save(User entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO user VALUES(?,?,?,?)",entity.getUserId(),entity.getUserName(),entity.getUserType(),entity.getPassword());
    }

    @Override
    public boolean update(User dto) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
    @Override
    public  User setLoginDetail(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT user_id,user_name,user_type,password FROM user WHERE user_name = ?",userName);
        if (resultSet.next()) {
            User user = new User(resultSet.getString("user_id"),resultSet.getString("user_name"),resultSet.getString("user_type"),resultSet.getString("password"));
            return user;
        } else {
            return null;
        }
    }
    @Override
    public  List<String> getIds() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT user_id FROM user");
        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    @Override
    public  String generateId() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT user_id  FROM user ORDER BY user_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("user_id");
            int newAppointmentId = Integer.parseInt(id.replace("U", "")) + 1;
            return String.format("U%03d", newAppointmentId);
        } else {
            return "U001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }


}

