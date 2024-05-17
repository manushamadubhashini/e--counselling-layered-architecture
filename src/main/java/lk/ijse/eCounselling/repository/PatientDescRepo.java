package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.PatientDesc;
import lk.ijse.eCounselling.model.TreatmentDesc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDescRepo {
    public static List<PatientDesc> getAll() throws SQLException {
        String sql = "SELECT p.pa_id, r.rep_id, p.Pa_name, p.Pa_DOB, p. Pa_address, p.pa_contact, p.pa_status, r.gender, r.description FROM patient p JOIN report r ON p.pa_id = r.pa_id";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PatientDesc> patientDescList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString("pa_id");
            String rid=resultSet.getString("rep_id");
            String name = resultSet.getString("pa_name");
            Date dob = resultSet.getDate("pa_dob");
            String address=resultSet.getString("pa_address");
            String contact=resultSet.getString("pa_contact");
            String status=resultSet.getString("pa_status");
            String gender=resultSet.getString("gender");
            String desc=resultSet.getString("description");

            PatientDesc patientDesc = new PatientDesc(id,rid,name,dob,address,contact, status,gender,desc);
            patientDescList.add(patientDesc);
        }
        return patientDescList;
    }
}
