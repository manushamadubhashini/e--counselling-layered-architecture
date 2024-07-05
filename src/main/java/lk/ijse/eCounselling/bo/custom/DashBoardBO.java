package lk.ijse.eCounselling.bo.custom;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import lk.ijse.eCounselling.bo.SuperBO;

import java.sql.SQLException;

public interface DashBoardBO extends SuperBO {
    public void getAppointmentCount(BarChart<String,Number> barChart) throws SQLException;

    public void getTreatmentCount(PieChart pieChart) throws SQLException;

    public int getPatientCount() throws SQLException;

    public int getAppointmentCount() throws SQLException;

}
