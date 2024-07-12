package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dto.TreatmentDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TreatmentDescBO extends SuperBO {
    public ArrayList<TreatmentDescDTO> getAll() throws SQLException;
}
