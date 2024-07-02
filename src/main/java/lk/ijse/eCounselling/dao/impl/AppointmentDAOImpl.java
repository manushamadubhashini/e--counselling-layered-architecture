package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.custom.AppointmentDAO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.EmployeeDTO;
import lk.ijse.eCounselling.entity.Appointment;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentDAOImpl implements AppointmentDAO {
    @Override
    public ArrayList<Appointment> getAll() throws SQLException {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        ResultSet resultSet= SQLUtil.execute("SELECT * FROM Appointment");
        while (resultSet.next()) {
            Appointment appointment = new Appointment(resultSet.getString("app_id"), resultSet.getString("app_type"), resultSet.getDate("app_date").toLocalDate(), resultSet.getString("app_time"),resultSet.getString("emp_id"),resultSet.getString("pa_id"));
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    @Override
    public boolean save(Appointment entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Appointment (app_id, app_type, app_date, app_time, emp_id,pa_id ) VALUES(?, ?, ?, ?, ?, ?)",entity.getId(),entity.getType(),entity.getDate(),entity.getTime(),entity.getEid(),entity.getPid());

    }
    @Override
    public boolean update(Appointment entity) throws SQLException {
        return SQLUtil.execute("UPDATE Appointment SET  app_type  = ?,app_date  = ?, app_time= ?,emp_id= ?,pa_id= ? WHERE app_id  = ?",entity.getType(),entity.getDate(),entity.getTime(),entity.getEid(),entity.getPid(),entity.getId());
    }
    @Override
    public String generateId() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT app_id  FROM Appointment ORDER BY app_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("app_id");
            int newAppointmentId = Integer.parseInt(id.replace("A", "")) + 1;
            return String.format("A%03d", newAppointmentId);
        } else {
            return "A001";
        }
    }
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Appointment WHERE app_id = ?",id);
    }


}
