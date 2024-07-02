package lk.ijse.eCounselling.dao;

import lk.ijse.eCounselling.dao.impl.AppointmentDAOImpl;
import lk.ijse.eCounselling.dao.impl.EmployeeDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory=new DAOFactory() : daoFactory;
    }
    public enum DAOType{
        APPOINTMENT,EMPLOYEE
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case APPOINTMENT:
                return new AppointmentDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
        }
        return null;
    }

}
