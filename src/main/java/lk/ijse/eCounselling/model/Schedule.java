package lk.ijse.eCounselling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Schedule {
    private String id;
    private LocalDate date;
    private String stime;
    private String etime;
    private String eid;
}
