package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.EmployeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public boolean save(EmployeeDTO employee) throws SQLException;

    public  boolean update(EmployeeDTO employee) throws SQLException;

    public  boolean delete(String id) throws SQLException;

    public ArrayList<EmployeeDTO> getAll() throws SQLException;

    public  String generateId() throws SQLException;

}
