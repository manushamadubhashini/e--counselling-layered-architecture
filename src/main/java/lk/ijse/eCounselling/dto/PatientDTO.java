package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import java.util.Date;
 @NoArgsConstructor
 @AllArgsConstructor
 @Data
public class PatientDTO {
    private String id;
    private String name;
    private LocalDate dob;
    private String address;
    private  String contact;
    private String status;


}
