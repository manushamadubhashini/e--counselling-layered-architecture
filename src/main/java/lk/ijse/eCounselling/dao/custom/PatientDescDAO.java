package lk.ijse.eCounselling.dao.custom;

import lk.ijse.eCounselling.dao.SuperDAO;
import lk.ijse.eCounselling.dto.PatientDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientDescDAO extends SuperDAO {
    public ArrayList<PatientDescDTO> getAll() throws SQLException;
}
