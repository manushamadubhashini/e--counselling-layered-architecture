package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.Schedule;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleRepo {
    public static boolean save(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule(sch_id, sch_date,start_time,end_time,emp_id) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, schedule.getId());
        pstm.setObject(2, schedule.getDate());
        pstm.setObject(3, schedule.getStime());
        pstm.setObject(4, schedule.getEtime());
        pstm.setObject(5,schedule.getEid());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID, LocalDate date, String STime, String ETime, String eid) throws SQLException {
        String sql = "UPDATE schedule SET  sch_date  = ?,start_time  = ?,end_time= ?,emp_id=?  WHERE sch_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, date);
        pstm.setObject(2, STime);
        pstm.setObject(3,ETime );
        pstm.setObject(4,eid );
        pstm.setObject(5,ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM schedule WHERE sch_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<Schedule> getAll() throws SQLException {
        String sql = "SELECT * FROM schedule";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Schedule> scheduleList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            Date date = resultSet.getDate(2);
            String STime = resultSet.getString(3);
            String ETime = resultSet.getString(4);
            String eid=resultSet.getString(5);

            Schedule schedule = new Schedule(id, date.toLocalDate(),STime,ETime,eid);
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT sch_id  FROM schedule ORDER BY sch_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("sch_id");
            int newScheduleId = Integer.parseInt(id.replace("H", "")) + 1;
            return String.format("H%03d", newScheduleId);
        } else {
            return "H001";
        }
    }
}
