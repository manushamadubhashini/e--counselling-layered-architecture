package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.TreatmentMethodDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentMethodDetailRepo {
    public static boolean save(TreatmentMethodDetail treatmentMethodDetail) throws SQLException {
        String sql = "INSERT INTO treatment_details (treat_id,treatm_id,treat_duration) VALUES(?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, treatmentMethodDetail.getId());
        pstm.setObject(2, treatmentMethodDetail.getMid());
        pstm.setObject(3, treatmentMethodDetail.getDuration());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID,String MID,int duration) throws SQLException {
        String sql = "UPDATE treatment_details SET  treat_duration  = ? WHERE  treat_id = ? AND treatm_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, duration);
        pstm.setObject(2, ID);
        pstm.setObject(3,MID );
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id,String MID) throws SQLException {
        String sql = "DELETE FROM  treatment_details WHERE treat_id= ? AND treatm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        pstm.setObject(2,MID);

        return pstm.executeUpdate() > 0;
    }

    public static List<TreatmentMethodDetail> getAll() throws SQLException {
        String sql = "SELECT * FROM  treatment_details";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TreatmentMethodDetail> treatmentMethodDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String mid = resultSet.getString(2);
            int duration = resultSet.getInt(3);

            TreatmentMethodDetail treatmentMethodDetail = new TreatmentMethodDetail(id, mid,duration);
            treatmentMethodDetailsList.add(treatmentMethodDetail);
        }
        return treatmentMethodDetailsList;
    }



}
