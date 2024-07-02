package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDesc {
    private String id;
    private String rid;
    private String name;
    private Date dob;
    private String address;
    private  String contact;
    private String status;
    private String gender;
    private String des;
}
