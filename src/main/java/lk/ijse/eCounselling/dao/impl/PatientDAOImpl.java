package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.PatientDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.PatientDTO;
import lk.ijse.eCounselling.entity.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public boolean save(Patient entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Patient (pa_id , Pa_name , Pa_DOB , Pa_address,pa_contact,pa_status) VALUES(?, ?, ?, ?, ?,?)",entity.getId(),entity.getName(),entity.getDob(),entity.getAddress(),entity.getContact(),entity.getStatus());
    }
    @Override
    public boolean update(Patient entity) throws SQLException {
        return SQLUtil.execute("UPDATE  Patient SET  Pa_name = ?,Pa_DOB = ?, Pa_address = ?,pa_contact = ?,pa_status = ?  WHERE pa_id  = ?",entity.getName(),entity.getDob(),entity.getAddress(),entity.getContact(),entity.getStatus(),entity.getId());

    }
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Patient WHERE pa_id = ?",id);
    }
    @Override
    public  ArrayList<Patient> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Patient");
        ArrayList<Patient> patientList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            Date dob = resultSet.getDate(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);
            String status = resultSet.getString(6);
            Patient patient = new Patient(id, name, dob.toLocalDate(), address, contact, status);
            patientList.add(patient);

        }
        return patientList;
    }
    @Override
    public String generateId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT pa_id  FROM Patient ORDER BY pa_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("pa_id");
            int newSessionId = Integer.parseInt(id.replace("P", "")) + 1;
            return String.format("P%03d", newSessionId);
        } else {
            return "P001";
        }
    }
    @Override
    public  List<String> getIds() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT pa_id FROM Patient");
        List<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}

