package lk.ijse.eCounselling.dao.custom;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import lk.ijse.eCounselling.dao.SuperDAO;

import java.sql.SQLException;

public interface DashBoarDAO extends SuperDAO {
    public void getAppointmentCount(BarChart<String,Number> barChart) throws SQLException;
    public void getTreatmentCount(PieChart pieChart) throws SQLException;
    public int getPatientCount() throws SQLException;
    public int getAppointmentCount() throws SQLException;
    public int getSessionCount() throws SQLException;
}
