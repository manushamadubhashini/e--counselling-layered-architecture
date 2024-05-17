package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.TreatmentDesc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDescRepo {
    public static List<TreatmentDesc> getAll() throws SQLException {
        String sql = "SELECT t.treat_id, tm.treatm_id, t.treat_status, tm.treatm_description, td.treat_duration FROM treatments t JOIN treatment_details td ON t.treat_id = td.treat_id JOIN treatment_method tm ON td.treatm_id = tm.treatm_id";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TreatmentDesc> treatmentDescList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString("t.treat_id");
            String mid=resultSet.getString("tm.treatm_id");
            String status = resultSet.getString("t.treat_status");
            String methodDescription = resultSet.getString("tm.treatm_description");
            int duration = resultSet.getInt("td.treat_duration");

            TreatmentDesc treatmentDesc = new TreatmentDesc(id,mid, status, methodDescription, duration);
            treatmentDescList.add(treatmentDesc);
        }
        return treatmentDescList;
    }
}