package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dto.PatientDTO;
import lk.ijse.eCounselling.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientBO extends SuperBO {
    public boolean save(PatientDTO entity) throws SQLException;
    public boolean update(PatientDTO entity) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public String generateId() throws SQLException;
    public List<String> getIds() throws SQLException;
    public ArrayList<PatientDTO> getAll() throws SQLException;
}
