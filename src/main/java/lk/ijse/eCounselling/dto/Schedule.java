package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
