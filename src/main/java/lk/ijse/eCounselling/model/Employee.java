package lk.ijse.eCounselling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Employee {
    private String id;
    private String name;
    private  Date DOB;
    private String address;
    private String contact;
    private String position;
    private Date joinDate;
    private String uid;


}
