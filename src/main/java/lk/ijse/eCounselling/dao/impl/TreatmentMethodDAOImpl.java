package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.TreatmentMethodDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentMethodDTO;
import lk.ijse.eCounselling.entity.TreatmentMethod;

import java.sql.*;
import java.util.ArrayList;

public class TreatmentMethodDAOImpl implements TreatmentMethodDAO {
    @Override
    public  boolean save(TreatmentMethod entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO TreatmentMethod (treatm_id,treatm_description) VALUES(?, ?)",entity.getMid(),entity.getDescription());

    }
    @Override
    public  boolean update(TreatmentMethod entity ) throws SQLException {
       return SQLUtil.execute("UPDATE TreatmentMethod SET treatm_description = ? WHERE treatm_id = ?",entity.getDescription(),entity.getMid());
    }
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM TreatmentMethod WHERE treatm_id = ?",id);
    }
    @Override
    public ArrayList<TreatmentMethod> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM TreatmentMethod");
        ArrayList<TreatmentMethod> treatmentMethodList = new ArrayList<>();
        while (resultSet.next()) {
            treatmentMethodList.add(new TreatmentMethod(resultSet.getString("treatm_id"),resultSet.getString("treatm_description")));
        }
        return treatmentMethodList;
    }
    @Override
    public String generateId() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT  treatm_id FROM TreatmentMethod ORDER BY treatm_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("treatm_id");
            int newScheduleId = Integer.parseInt(id.replace("M", "")) + 1;
            return String.format("M%03d", newScheduleId);
        } else {
            return "M001";
        }
    }

}
