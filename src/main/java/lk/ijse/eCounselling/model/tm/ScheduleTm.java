package lk.ijse.eCounselling.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleTm {
    private String id;
    private LocalDate date;
    private String  stime;
    private String etime;
    private String eid;
}
