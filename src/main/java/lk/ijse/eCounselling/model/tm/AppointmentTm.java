package lk.ijse.eCounselling.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentTm {
    private String id;
    private String type;
    private LocalDate date;
    private String time;
    private String eid;
    private String pid;
}
