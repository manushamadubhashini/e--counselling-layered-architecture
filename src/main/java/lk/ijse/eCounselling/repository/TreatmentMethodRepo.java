package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.TreatmentMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentMethodRepo {
    public static boolean save(TreatmentMethod treatmentMethod) throws SQLException {
        String sql = "INSERT INTO treatment_method (treatm_id,treatm_description) VALUES(?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, treatmentMethod.getMid());
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

    public static ArrayList<TreatmentMethod> getAll() throws SQLException {
        String sql = "SELECT * FROM treatment_method";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<TreatmentMethod> treatmentMethodList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);

            TreatmentMethod treatmentMethod = new TreatmentMethod(id, description);
            treatmentMethodList.add(treatmentMethod);
        }
        return treatmentMethodList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT  treatm_id FROM treatment_method ORDER BY treatm_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("treatm_id");
            int newScheduleId = Integer.parseInt(id.replace("M", "")) + 1;
            return String.format("M%03d", newScheduleId);
        } else {
            return "M001";
        }
    }

}
