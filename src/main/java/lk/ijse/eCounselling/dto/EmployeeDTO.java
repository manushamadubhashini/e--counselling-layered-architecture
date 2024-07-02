package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDTO {
    private String id;
    private String name;
    private  String DOB;
    private String address;
    private String contact;
    private String position;
    private LocalDate joinDate;
    private String uid;


}
