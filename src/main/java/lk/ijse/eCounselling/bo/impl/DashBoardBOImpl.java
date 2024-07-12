package lk.ijse.eCounselling.bo.impl;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import lk.ijse.eCounselling.bo.custom.DashBoardBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.DashBoarDAO;

import java.sql.SQLException;

public class DashBoardBOImpl implements DashBoardBO {

    DashBoarDAO dashBoarDAO=(DashBoarDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.DASHBOARD);
    @Override
    public void getAppointmentCount(BarChart<String, Number> barChart) throws SQLException {
        dashBoarDAO.getAppointmentCount(barChart);

    }
    @Override
    public void getTreatmentCount(PieChart pieChart) throws SQLException {
        dashBoarDAO.getTreatmentCount(pieChart);

    }
    @Override
    public int getPatientCount() throws SQLException{
        return dashBoarDAO.getPatientCount();
    }
    @Override
    public int getAppointmentCount() throws SQLException{
        return dashBoarDAO.getAppointmentCount();
    }

    @Override
    public int getSessionCount() throws SQLException {
        return dashBoarDAO.getSessionCount();
    }
}
