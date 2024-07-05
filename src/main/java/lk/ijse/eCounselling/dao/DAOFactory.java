package lk.ijse.eCounselling.dao;

import lk.ijse.eCounselling.dao.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        APPOINTMENT,EMPLOYEE,SCHEDULE,SESSION,USER,DASHBOARD,TREATMENTMETHOD
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case APPOINTMENT:
                return new AppointmentDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SCHEDULE:
                return new ScheduleDAOImpl();
            case SESSION:
                return new SessionDAOImpl();
            case USER:
                return new UserDAOImpl();
            case DASHBOARD:
                return new DashBoardDAOImpl();
            case TREATMENTMETHOD:
                return new TreatmentMethodDAOImpl();
        }
        return null;
    }

}
