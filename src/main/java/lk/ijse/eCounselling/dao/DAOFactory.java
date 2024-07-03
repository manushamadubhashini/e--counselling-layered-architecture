package lk.ijse.eCounselling.dao;

import lk.ijse.eCounselling.dao.impl.AppointmentDAOImpl;
import lk.ijse.eCounselling.dao.impl.EmployeeDAOImpl;
import lk.ijse.eCounselling.dao.impl.ScheduleDAOImpl;
import lk.ijse.eCounselling.dao.impl.SessionDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        APPOINTMENT,EMPLOYEE,SCHEDULE,SESSION
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
        }
        return null;
    }

}
