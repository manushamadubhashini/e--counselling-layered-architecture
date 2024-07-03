package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.ScheduleDAO;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.ScheduleDTO;
import lk.ijse.eCounselling.entity.Schedule;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleDAOImpl implements ScheduleDAO {
    @Override
    public  boolean save(Schedule entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Schedule(sch_id, sch_date,start_time,end_time,emp_id) VALUES(?, ?, ?, ?, ?)",entity.getId(),entity.getDate(),entity.getStime(),entity.getEtime(),entity.getEid());
    }
    @Override
    public  boolean update(Schedule entity) throws SQLException {
        return SQLUtil.execute("UPDATE Schedule SET  sch_date  = ?,start_time  = ?,end_time= ?,emp_id=?  WHERE sch_id=?",entity.getDate(),entity.getStime(),entity.getEtime(),entity.getEid(),entity.getId());
    }
    @Override
    public  boolean delete(String id) throws SQLException {
       return SQLUtil.execute("DELETE FROM Schedule WHERE sch_id = ?",id);
    }
    @Override
    public  ArrayList<Schedule> getAll() throws SQLException {
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM Schedule");
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        while (resultSet.next()) {
            scheduleList.add(new Schedule(resultSet.getString("sch_id"), resultSet.getDate("sch_date").toLocalDate(),resultSet.getString("start_time"),resultSet.getString("end_time"),resultSet.getString("emp_id")));
        }
        return scheduleList;
    }
    @Override
    public  String generateId() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT sch_id  FROM Schedule ORDER BY sch_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("sch_id");
            int newScheduleId = Integer.parseInt(id.replace("H", "")) + 1;
            return String.format("H%03d", newScheduleId);
        } else {
            return "H001";
        }
    }
}

