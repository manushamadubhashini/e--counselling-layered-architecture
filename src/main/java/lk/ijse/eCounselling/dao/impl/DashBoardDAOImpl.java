package lk.ijse.eCounselling.dao.impl;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.DashBoarDAO;
import lk.ijse.eCounselling.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoardDAOImpl implements DashBoarDAO {
    @Override
    public void getAppointmentCount(BarChart<String,Number> barChart) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT DATE_FORMAT(app_date, '%m') as app_month, COUNT(app_id) as Count FROM Appointment GROUP BY app_month");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Appointments per Month");
        while (resultSet.next()) {
            String appMonth = resultSet.getString("app_month");
            int count = resultSet.getInt("Count");
            series.getData().add(new XYChart.Data<>(appMonth, count));
        }
        barChart.getData().add(series);

    }
    @Override
    public void getTreatmentCount(PieChart pieChart) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT treat_status, COUNT(treat_status) as treatment_count FROM Treatment GROUP BY treat_status");
        while (resultSet.next()) {
            String treatmentName = resultSet.getString("treat_status");
            int count = resultSet.getInt("treatment_count");
            PieChart.Data slice = new PieChart.Data(treatmentName, count);
            pieChart.getData().add(slice);
        }
    }
    @Override
    public int getPatientCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS patient_count FROM Patient");
        int patientCount = 0;
        if (resultSet.next()) {
            patientCount = resultSet.getInt("patient_count");
        }
        return patientCount;
    }
    @Override
    public int getAppointmentCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute( "SELECT COUNT(*) AS appointment_count FROM Appointment");
        int appointmentCount = 0;
        if (resultSet.next()) {
            appointmentCount = resultSet.getInt("appointment_count");
        }
        return appointmentCount;
    }

    @Override
    public int getSessionCount() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS session_count FROM  Session");
        int sessionCount = 0;
        if (resultSet.next()) {
            sessionCount = resultSet.getInt("session_count");
        }
        return sessionCount;
    }
}
