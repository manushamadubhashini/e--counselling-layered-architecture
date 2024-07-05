package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.TreatmentMethodDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TreatmentMethodBO extends SuperBO {
    public  boolean save(TreatmentMethodDTO entity) throws SQLException;
    public  boolean update(TreatmentMethodDTO entity ) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public ArrayList<TreatmentMethodDTO> getAll() throws SQLException;
    public String generateId() throws SQLException;

}
