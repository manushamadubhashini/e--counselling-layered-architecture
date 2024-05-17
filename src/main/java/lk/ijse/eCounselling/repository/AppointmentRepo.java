package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepo {
    public static boolean save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment (app_id, app_type, app_date, app_status, app_duaration,emp_id,pa_id ) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, appointment.getId());
        pstm.setObject(2, appointment.getType());
        pstm.setObject(3, appointment.getDate());
        pstm.setObject(4, appointment.getStatus());
        pstm.setObject(5, appointment.getDuration());
        pstm.setObject(6,appointment.getEid());
        pstm.setObject(7,appointment.getPid());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String ID, String type, Date date, String status,int duration,String eid,String pid) throws SQLException {
        String sql = "UPDATE appointment SET  app_type  = ?,app_date  = ?, app_status= ?, app_duration = ?,emp_id= ?,pa_id= ? WHERE app_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, type);
        pstm.setObject(2, date);
        pstm.setObject(3,status );
        pstm.setObject(4, duration);
        pstm.setObject(5,eid);
        pstm.setObject(6,pid);
        pstm.setObject(7,ID);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE app_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM appointment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Appointment> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String status = resultSet.getString(4);
            int duration = resultSet.getInt(5);
            String eid=resultSet.getString(6);
            String pid=resultSet.getString(7);

            Appointment appointment = new Appointment(id, type,date,status,duration,eid,pid);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }



}


