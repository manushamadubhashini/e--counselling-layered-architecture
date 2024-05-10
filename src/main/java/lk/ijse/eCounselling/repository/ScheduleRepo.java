package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Schedule;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepo {
    public static boolean save(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule(sch_id, sch_date , start_time, end_time) VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, schedule.getId());
        pstm.setObject(2, schedule.getDate());
        pstm.setObject(3, schedule.getEtime());
        pstm.setObject(4, schedule.getEtime());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID,Date date, String STime, String ETime) throws SQLException {
        String sql = "UPDATE schedule SET  sch_date  = ?,start_time  = ?,end_time= ? WHERE sch_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, date);
        pstm.setObject(2, STime);
        pstm.setObject(3,ETime );
        pstm.setObject(4,ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM schedule WHERE sch_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Schedule> getAll() throws SQLException {
        String sql = "SELECT * FROM schedule";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Schedule> scheduleList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            Date date = resultSet.getDate(2);
            String STime = resultSet.getString(3);
            String ETime = resultSet.getString(4);

            Schedule schedule = new Schedule(id, date,STime,ETime);
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

}
