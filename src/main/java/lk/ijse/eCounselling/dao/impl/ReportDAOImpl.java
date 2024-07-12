package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.ReportDAO;
import lk.ijse.eCounselling.dto.ReportDTO;
import lk.ijse.eCounselling.entity.Report;

import java.sql.*;
import java.util.ArrayList;

public class ReportDAOImpl implements ReportDAO {
    @Override
    public boolean save(Report entity) throws SQLException {
        return SQLUtil.execute( "INSERT INTO Report (rep_id,gender,description, pa_id) VALUES(?, ?, ?, ?)",entity.getRid(),entity.getGender(),entity.getDes(),entity.getId());
    }
    @Override
    public  boolean update(Report entity) throws SQLException {
        return SQLUtil.execute("UPDATE Report SET gender = ?,description = ?,pa_id = ? WHERE rep_id  = ?",entity.getGender(),entity.getDes(),entity.getId(),entity.getRid());

    }
    @Override
    public  boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Report WHERE rep_id = ?");
    }
    @Override
    public  ArrayList<Report> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
    @Override
    public String generateId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT rep_id  FROM Report ORDER BY rep_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("rep_id");
            int newSessionId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R%03d", newSessionId);
        } else {
            return "R001";
        }
    }



}

