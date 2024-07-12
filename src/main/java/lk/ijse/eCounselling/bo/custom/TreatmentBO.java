package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dto.TreatmentDTO;
import lk.ijse.eCounselling.entity.TreatmentMethod;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TreatmentBO extends SuperBO {
    public ArrayList<TreatmentMethod> getAll() throws SQLException;

    public boolean TreatmentSave(TreatmentDTO treatmentDTO) throws SQLException;

    public boolean TreatmentUpdate(TreatmentDTO treatmentDTO) throws SQLException;

    public boolean TreatmentDelete(String id) throws SQLException;

    public  String generateId() throws SQLException;

}
