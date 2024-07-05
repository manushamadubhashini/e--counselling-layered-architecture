package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.UserBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.UserDAO;
import lk.ijse.eCounselling.dto.UserDTO;
import lk.ijse.eCounselling.entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserBoImpl implements UserBO {

    UserDAO userDAO=(UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);
    @Override
    public boolean save(UserDTO entity) throws SQLException {
        return userDAO.save(new User(entity.getUserId(),entity.getUserName(),entity.getUserType(),entity.getPassword()));
    }

    @Override
    public UserDTO setLoginDetail(String userName) throws SQLException {
        User user=userDAO.setLoginDetail(userName);
        UserDTO userDTO=new UserDTO(user.getUserId(),user.getUserName(),user.getUserType(),user.getPassword());
        return userDTO;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return userDAO.getIds();
    }

    @Override
    public String generateId() throws SQLException {
        return userDAO.generateId();
    }

}
