package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentDesc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentDescRepo {
    public static ArrayList<TreatmentDesc> getAll() throws SQLException {
        String sql = "SELECT t.treat_id,tm.treatm_id, t.treat_status, td.treat_duration ,t.pa_id FROM treatments t JOIN treatment_details td ON t.treat_id = td.treat_id JOIN treatment_method tm ON td.treatm_id = tm.treatm_id";

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            ArrayList<TreatmentDesc> treatmentDescList = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("t.treat_id");
                String mid=resultSet.getString("tm.treatm_id");
                String status = resultSet.getString("t.treat_status");
                int duration = resultSet.getInt("td.treat_duration");
                String pid=resultSet.getString("t.pa_id");

                TreatmentDesc treatmentDesc = new TreatmentDesc(id,mid, status, duration,pid);
                treatmentDescList.add(treatmentDesc);
            }
            connection.commit();
            return treatmentDescList;
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Error fetching treatment descriptions", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}