package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Treatment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        pstm.setObject(2, pstm);
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



}
