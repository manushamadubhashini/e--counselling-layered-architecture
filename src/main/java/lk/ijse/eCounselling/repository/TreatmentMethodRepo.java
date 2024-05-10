package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.TreatmentMethod;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentMethodRepo {
    public static boolean save(TreatmentMethod treatmentMethod) throws SQLException {
        String sql = "INSERT INTO treatment_method (treatm_id,treatm_description) VALUES(?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, treatmentMethod.getId());
        pstm.setObject(2, treatmentMethod.getDescription());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID,String description ) throws SQLException {
        String sql = "UPDATE treatment_method SET treatm_description = ? WHERE treatm_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, description);
        pstm.setObject(2, ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM treatment_method WHERE treatm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<TreatmentMethod> getAll() throws SQLException {
        String sql = "SELECT * FROM treatment_method";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<TreatmentMethod> treatmentMethodList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);

            TreatmentMethod treatmentMethod = new TreatmentMethod(id, description);
            treatmentMethodList.add(treatmentMethod);
        }
        return treatmentMethodList;
    }



}
