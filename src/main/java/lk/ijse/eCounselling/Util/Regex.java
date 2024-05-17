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



}
