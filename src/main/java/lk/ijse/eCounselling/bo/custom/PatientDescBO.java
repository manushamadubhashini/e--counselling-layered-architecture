package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dto.PatientDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientDescBO extends SuperBO {
    public ArrayList<PatientDescDTO> getAll() throws SQLException;
}
