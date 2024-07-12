package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dto.PatientDTO;
import lk.ijse.eCounselling.dto.ReportDTO;

import java.sql.SQLException;
import java.util.List;

public interface ReportBO extends SuperBO {
    public boolean save(ReportDTO entity) throws SQLException;
    public boolean update(ReportDTO entity) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public String generateId() throws SQLException;
}
