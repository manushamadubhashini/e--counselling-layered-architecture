package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.SessionDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.SessionDTO;
import lk.ijse.eCounselling.entity.Session;

import java.sql.*;
import java.util.ArrayList;

public class SessionDAOImpl implements SessionDAO {
    public  boolean save(Session entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Session (ses_id, ses_type, ses_date, ses_duration,emp_id,pa_id) VALUES(?, ?, ?, ?,?,?)",entity.getId(),entity.getType(),entity.getDate(),entity.getDuration(),entity.getEid(),entity.getPid());

    }
    public  boolean update(Session entity) throws SQLException {
        return SQLUtil.execute("UPDATE Session SET  ses_type = ?, ses_date = ?,ses_duration= ?,emp_id=?,pa_id=?  WHERE ses_id = ?",entity.getType(),entity.getDate(),entity.getDuration(),entity.getEid(),entity.getPid(),entity.getId());

    }
    public  boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Session WHERE ses_id = ?",id);
    }

    public  ArrayList<Session> getAll() throws SQLException {
        ResultSet resultSet =SQLUtil.execute("SELECT * FROM Session");
        ArrayList<Session> sessionList = new ArrayList<>();
        while (resultSet.next()) {
            sessionList.add(new Session(resultSet.getString("ses_id"),resultSet.getString("ses_type"), resultSet.getDate("ses_date").toLocalDate(),resultSet.getInt("ses_duration"),resultSet.getString("emp_id"),resultSet.getString("pa_id")));
        }
        return sessionList;
    }
    public  String generateId() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT ses_id  FROM Session ORDER BY ses_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ses_id");
            int newSessionId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S%03d", newSessionId);
        } else {
            return "S001";
        }
    }

}

