package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepo {
    public static boolean save(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patient (pa_id , Pa_name , Pa_DOB , Pa_address,pa_contact,pa_status) VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, patient.getId());
        pstm.setObject(2, patient.getName());
        pstm.setObject(3, patient.getDob());
        pstm.setObject(4, patient.getAddress());
        pstm.setObject(5, patient.getContact());
        pstm.setObject(6, patient.getStatus());
        return pstm.executeUpdate() > 0;


    }

    public static boolean update(String ID, String name, Date dob, String address, String contact, String status) throws SQLException {
        String sql = "UPDATE  Patient SET  Pa_name = ?,Pa_DOB = ?, Pa_address = ?,pa_contact = ?,pa_status = ?  WHERE pa_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, name);
        pstm.setObject(2, dob);
        pstm.setObject(3, address);
        pstm.setObject(4, contact);
        pstm.setObject(5, status);
        pstm.setObject(6, ID);
        return pstm.executeUpdate() > 0;


    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Patient WHERE pa_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<Patient> getAll() throws SQLException {
        String sql = "SELECT * FROM Patient";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Patient> patientList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            Date dob = resultSet.getDate(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);
            String status = resultSet.getString(6);

            Patient patient = new Patient(id, name, dob, address, contact, status);
            patientList.add(patient);

        }
        return patientList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT pa_id FROM Patient";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }



}
