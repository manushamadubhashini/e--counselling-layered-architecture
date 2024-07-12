package lk.ijse.eCounselling.dao.custom;

import lk.ijse.eCounselling.dao.SuperDAO;
import lk.ijse.eCounselling.dto.TreatmentDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TreatmentDescDAO extends SuperDAO {
    public ArrayList<TreatmentDescDTO> getAll() throws SQLException;
}
