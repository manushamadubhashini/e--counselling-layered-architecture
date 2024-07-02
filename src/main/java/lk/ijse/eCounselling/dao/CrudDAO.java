package lk.ijse.eCounselling.dao;

import lk.ijse.eCounselling.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    public ArrayList<T> getAll() throws SQLException;

    public boolean save(T dto) throws SQLException;

    public boolean update(T dto) throws SQLException;

    public String generateId() throws SQLException;

    public boolean delete(String id) throws SQLException;


}
