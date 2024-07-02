package lk.ijse.eCounselling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Appointment {
    private String id;
    private String type;
    private LocalDate date;
    private String time;
    private String eid;
    private String pid;
}
