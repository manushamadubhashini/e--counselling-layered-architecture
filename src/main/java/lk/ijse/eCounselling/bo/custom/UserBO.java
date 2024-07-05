package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    public  boolean save(UserDTO entity) throws SQLException;
    public  UserDTO setLoginDetail(String userName) throws SQLException;
    public List<String> getIds() throws SQLException;
    public  String generateId() throws SQLException;


}
