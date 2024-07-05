package lk.ijse.eCounselling.dao.custom;

import lk.ijse.eCounselling.dao.CrudDAO;
import lk.ijse.eCounselling.dto.UserDTO;
import lk.ijse.eCounselling.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
    public  User setLoginDetail(String userName) throws SQLException;
    public  List<String> getIds() throws SQLException;

}
