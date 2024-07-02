package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.AppointmentDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentRepo {
    public static boolean save(AppointmentDTO appointment) throws SQLException {
        String sql = "INSERT INTO appointment (app_id, app_type, app_date, app_time, emp_id,pa_id ) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, appointment.getId());
        pstm.setObject(2, appointment.getType());
        pstm.setObject(3, appointment.getDate());
        pstm.setObject(4, appointment.getTime());
        pstm.setObject(5,appointment.getEid());
        pstm.setObject(6,appointment.getPid());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID, String type, LocalDate date, String time,String eid,String pid) throws SQLException {
        String sql = "UPDATE appointment SET  app_type  = ?,app_date  = ?, app_time= ?,emp_id= ?,pa_id= ? WHERE app_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, type);
        pstm.setObject(2, date);
        pstm.setObject(3,time );
        pstm.setObject(4,eid);
        pstm.setObject(5,pid);
        pstm.setObject(6,ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE app_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<AppointmentDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM appointment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AppointmentDTO> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            AppointmentDTO appointment = new AppointmentDTO(resultSet.getString("app_id"), resultSet.getString("app_type"), resultSet.getDate("app_date").toLocalDate(), resultSet.getString("app_time"),resultSet.getString("emp_id"),resultSet.getString("pa_id"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT app_id  FROM appointment ORDER BY app_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("app_id");
            int newAppointmentId = Integer.parseInt(id.replace("A", "")) + 1;
            return String.format("A%03d", newAppointmentId);
        } else {
            return "A001";
        }
    }



}


