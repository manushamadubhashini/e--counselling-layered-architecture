package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.SessionDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SessionBO extends SuperBO {
    public  boolean save(SessionDTO entity) throws SQLException;

    public  boolean update(SessionDTO entity) throws SQLException;

    public  boolean delete(String id) throws SQLException;

    public ArrayList<SessionDTO> getAll() throws SQLException;

    public  String generateId() throws SQLException;


}
