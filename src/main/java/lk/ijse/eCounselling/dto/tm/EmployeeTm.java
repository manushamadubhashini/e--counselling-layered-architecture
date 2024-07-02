package lk.ijse.eCounselling.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTm {
    private String id;
    private String name;
    private String DOB;
    private String address;
    private String contact;
    private String position;
    private LocalDate joinDate;
    private String uid;

}
