package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepo {
    public static boolean save(Treatment treatment) throws SQLException {
        String sql = "INSERT INTO treatments (treat_id ,treat_status, pa_id   ) VALUES(?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, treatment.getId());
        pstm.setObject(2, treatment.getStatus());
        pstm.setObject(3,treatment.getPid());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID, String status,String pid) throws SQLException {
        String sql = "UPDATE treatments SET  treat_status  = ?,pa_id = ? WHERE treat_id   = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, status);
        pstm.setObject(2, pid);
        pstm.setObject(3,ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM treatments WHERE treat_id  = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Treatment> getAll() throws SQLException {
        String sql = "SELECT * FROM treatments ";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Treatment> treatmentList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String status = resultSet.getString(2);
            String pid=resultSet.getString(3);
            Treatment treatment = new Treatment(id,status,pid);
            treatmentList.add(treatment);
        }
        return treatmentList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT treat_id  FROM treatments ORDER BY treat_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("treat_id");
            int newScheduleId = Integer.parseInt(id.replace("T", "")) + 1;
            return String.format("T%03d", newScheduleId);
        } else {
            return "T001";
        }
    }



}
