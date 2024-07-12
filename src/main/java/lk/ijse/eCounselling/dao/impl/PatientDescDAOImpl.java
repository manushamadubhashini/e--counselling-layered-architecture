package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.PatientDescDAO;
import lk.ijse.eCounselling.dto.PatientDescDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDescDAOImpl implements PatientDescDAO {
    @Override
    public ArrayList<PatientDescDTO> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT p.pa_id, r.rep_id, p.Pa_name, p.Pa_DOB, p. Pa_address, p.pa_contact, p.pa_status, r.gender, r.description FROM Patient p JOIN Report r ON p.pa_id = r.pa_id");
        ArrayList<PatientDescDTO> patientDescList = new ArrayList<>();
        while (resultSet.next()) {
            patientDescList.add(new PatientDescDTO(resultSet.getString("pa_id"),resultSet.getString("rep_id"),resultSet.getString("pa_name"), resultSet.getDate("pa_dob"),resultSet.getString("pa_address"),resultSet.getString("pa_contact"),resultSet.getString("pa_status"),resultSet.getString("gender"),resultSet.getString("description")));
        }
        return patientDescList;
    }
}

