package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.ScheduleDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleRepo {
    public static boolean save(ScheduleDTO schedule) throws SQLException {
        String sql = "INSERT INTO Schedule(sch_id, sch_date,start_time,end_time,emp_id) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, schedule.getId());
        pstm.setObject(2, schedule.getDate());
        pstm.setObject(3, schedule.getStime());
        pstm.setObject(4, schedule.getEtime());
        pstm.setObject(5,schedule.getEid());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(ScheduleDTO dto) throws SQLException {
        String sql = "UPDATE Schedule SET  sch_date  = ?,start_time  = ?,end_time= ?,emp_id=?  WHERE sch_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, dto.getDate());
        pstm.setObject(2, dto.getStime());
        pstm.setObject(3,dto.getEtime());
        pstm.setObject(4,dto.getEid() );
        pstm.setObject(5,dto.getId());
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Schedule WHERE sch_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<ScheduleDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM Schedule";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ScheduleDTO> scheduleList = new ArrayList<>();
        while (resultSet.next()) {
            scheduleList.add(new ScheduleDTO(resultSet.getString("sch_id"), resultSet.getDate("sch_date").toLocalDate(),resultSet.getString("start_time"),resultSet.getString("end_time"),resultSet.getString("emp_id")));
        }
        return scheduleList;
    }

    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT sch_id  FROM Schedule ORDER BY sch_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("sch_id");
            int newScheduleId = Integer.parseInt(id.replace("H", "")) + 1;
            return String.format("H%03d", newScheduleId);
        } else {
            return "H001";
        }
    }
}
