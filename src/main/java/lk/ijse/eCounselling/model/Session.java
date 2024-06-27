package lk.ijse.eCounselling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Session {
    private String id;
    private String type;
    private LocalDate date;
    private int duration;
    private String eid;
    private String pid;
}
