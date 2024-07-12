package lk.ijse.eCounselling.bo;

import lk.ijse.eCounselling.bo.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory=new BOFactory() : boFactory;
    }

    public enum BOType{
        APPOINTMENT,EMPLOYEE,SCHEDULE,SESSION,USER,DASHBOARD,TREATMENTMETHOD,TREATMENT,TREATMENTDESC,PATIENT,REPORT,PATIENTREPORTDESC
    }
    public SuperBO getBO(BOType boType){
        switch (boType){
            case APPOINTMENT:
                return new AppointmentBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SCHEDULE:
                return new ScheduleBOImpl();
            case SESSION:
                return new SessionBoImpl();
            case USER:
                return new UserBoImpl();
            case DASHBOARD:
                return new DashBoardBOImpl();
            case TREATMENTMETHOD:
                return new TreatmentMethodBOImpl();
            case TREATMENT:
                return new TreatmentBOImpl();
            case TREATMENTDESC:
                return new TreatmentDescBOImpl();
            case PATIENT:
                return new PatientBOImpl();
            case REPORT:
                return new ReportBOImpl();
            case PATIENTREPORTDESC:
                return new PatientDescBOImpl();
        }
        return null;
    }
}
