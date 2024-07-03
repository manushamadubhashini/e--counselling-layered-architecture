package lk.ijse.eCounselling.bo;

import lk.ijse.eCounselling.bo.impl.AppointmentBOImpl;
import lk.ijse.eCounselling.bo.impl.EmployeeBOImpl;
import lk.ijse.eCounselling.bo.impl.ScheduleBOImpl;
import lk.ijse.eCounselling.bo.impl.SessionBoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory=new BOFactory() : boFactory;
    }

    public enum BOType{
        APPOINTMENT,EMPLOYEE,SCHEDULE,SESSION
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
        }
        return null;
    }
}
