package lk.ijse.eCounselling.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientTm {
    private String id;
    private String name;
    private Date dob;
    private String address;
    private  String contact;
    private String status;
}
