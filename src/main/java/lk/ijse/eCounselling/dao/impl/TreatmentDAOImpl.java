package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.TreatmentDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentDTO;
import lk.ijse.eCounselling.entity.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAOImpl implements TreatmentDAO {
    @Override
    public  boolean save(Treatment entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Treatment (treat_id ,treat_status, pa_id) VALUES(?, ?,?)",entity.getId(),entity.getStatus(),entity.getPid());
    }
    @Override
    public  boolean update(Treatment entity) throws SQLException {
        return SQLUtil.execute("UPDATE Treatment SET  treat_status  = ?,pa_id = ? WHERE treat_id   = ?",entity.getStatus(),entity.getPid(),entity.getId());

    }
    @Override
    public  boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Treatment WHERE treat_id  = ?",id);
    }
    @Override
    public  ArrayList<Treatment> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Treatment");
        ArrayList<Treatment> treatmentList = new ArrayList<>();
        while (resultSet.next()) {
            treatmentList.add(new Treatment(resultSet.getString("treat_id"),resultSet.getString("treat_status"),resultSet.getString("pa_id")));
        }
        return treatmentList;
    }
    @Override
    public  String generateId() throws SQLException {
        ResultSet rst= SQLUtil.execute("SELECT treat_id  FROM Treatment ORDER BY treat_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("treat_id");
            int newScheduleId = Integer.parseInt(id.replace("T", "")) + 1;
            return String.format("T%03d", newScheduleId);
        } else {
            return "T001";
        }
    }



}


