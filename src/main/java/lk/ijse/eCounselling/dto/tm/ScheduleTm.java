package lk.ijse.eCounselling.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
