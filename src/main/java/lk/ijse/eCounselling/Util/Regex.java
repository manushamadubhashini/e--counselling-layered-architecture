package lk.ijse.eCounselling.Util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isValidUserId(String userId) {
        String regex = "^(U[0-9]{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userId);
        return matcher.matches();
    }
    public static boolean isAppointmentId(String app_id) {
        String regex = "^(A[0-9]{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(app_id);
        return matcher.matches();
    }
    public static boolean isDuration(int duration) {
        String regex = "^(?:[1-9]|1[0-9]|20)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.valueOf(duration));
        return matcher.matches();
    }


    public static boolean isPatientId(String pid) {
        String regex = "^(P[0-9]{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pid);
        return matcher.matches();
    }

    public static boolean isReportId(String rid) {
        String regex = "^(R[0-9]{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rid);
        return matcher.matches();
    }

    public static boolean isContact(String contact) {
        String regex = "^0[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();

    }
}
