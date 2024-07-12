package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.TreatmentMethodDetailDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentMethodDetailDTO;
import lk.ijse.eCounselling.entity.TreatmentMethodDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentMethodDetailDAOImpl implements TreatmentMethodDetailDAO {
    @Override
    public  boolean save(TreatmentMethodDetail entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO TreatmentDetail (treat_id,treatm_id,treat_duration) VALUES(?, ?,?)",entity.getId(),entity.getMid(),entity.getDuration());

    }
    @Override
    public  boolean update(TreatmentMethodDetail entity) throws SQLException {
        return SQLUtil.execute("UPDATE TreatmentDetail SET  treat_duration  = ? WHERE  treat_id = ? AND treatm_id = ?",entity.getDuration(),entity.getId(),entity.getMid());

    }

    @Override
    public String generateId() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM TreatmentDetail WHERE treat_id = ?",id);
    }
    @Override
    public  ArrayList<TreatmentMethodDetail> getAll() throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM  TreatmentDetail");
        ArrayList<TreatmentMethodDetail> treatmentMethodDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            treatmentMethodDetailsList.add(new TreatmentMethodDetail(resultSet.getString("treat_id"),resultSet.getString("treatm_id"),resultSet.getInt("treat_duration")));
        }
        return treatmentMethodDetailsList;
    }



}

