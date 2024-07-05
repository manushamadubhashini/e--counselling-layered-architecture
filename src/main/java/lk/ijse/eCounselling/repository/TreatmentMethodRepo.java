package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentMethodDTO;

import java.sql.*;
import java.util.ArrayList;

public class TreatmentMethodRepo {
    public static boolean save(TreatmentMethodDTO treatmentMethod) throws SQLException {
        String sql = "INSERT INTO TreatmentMethod (treatm_id,treatm_description) VALUES(?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, treatmentMethod.getMid());
        pstm.setObject(2, treatmentMethod.getDescription());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID,String description ) throws SQLException {
        String sql = "UPDATE TreatmentMethod SET treatm_description = ? WHERE treatm_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, description);
        pstm.setObject(2, ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM TreatmentMethod WHERE treatm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<TreatmentMethodDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM TreatmentMethod";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<TreatmentMethodDTO> treatmentMethodList = new ArrayList<>();
        while (resultSet.next()) {
            treatmentMethodList.add(new TreatmentMethodDTO(resultSet.getString("treatm_id"),resultSet.getString("treatm_description")));
        }
        return treatmentMethodList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT  treatm_id FROM TreatmentMethod ORDER BY treatm_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("treatm_id");
            int newScheduleId = Integer.parseInt(id.replace("M", "")) + 1;
            return String.format("M%03d", newScheduleId);
        } else {
            return "M001";
        }
    }

}
