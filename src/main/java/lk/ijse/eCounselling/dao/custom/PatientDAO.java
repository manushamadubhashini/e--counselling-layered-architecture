package lk.ijse.eCounselling.dao.custom;

import lk.ijse.eCounselling.dao.CrudDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.PatientDTO;
import lk.ijse.eCounselling.entity.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientDAO extends CrudDAO<Patient> {
    public  List<String> getIds() throws SQLException;
}
