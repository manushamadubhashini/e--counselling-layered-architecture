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
        APPOINTMENT,EMPLOYEE,SCHEDULE,SESSION,USER,DASHBOARD,TREATMENTMETHOD,TREATMENT,TREATMENTDETAIL,TREATMENTDESC,PATIENT,REPORT,PATIENTREPORTDESC
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
            case TREATMENT:
                return new TreatmentDAOImpl();
            case TREATMENTDETAIL:
                return new TreatmentMethodDetailDAOImpl();
            case TREATMENTDESC:
                return new TreatmentDescDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            case REPORT:
                return new ReportDAOImpl();
            case PATIENTREPORTDESC:
                return new PatientDescDAOImpl();
        }
        return null;
    }

}
