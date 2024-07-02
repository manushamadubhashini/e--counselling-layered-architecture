package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentDTO {
    private String id;
    private String type;
    private LocalDate date;
    private String time;
    private String eid;
    private String pid;
}
