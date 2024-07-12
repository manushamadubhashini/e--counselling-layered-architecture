package lk.ijse.eCounselling.entity;

import lk.ijse.eCounselling.dto.ReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
   private String id;
   private String name;
   private LocalDate dob;
   private String address;
   private  String contact;
   private String status;
}
