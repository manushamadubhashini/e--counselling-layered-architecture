package lk.ijse.eCounselling.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionTm {
    private String id;
    private String type;
    private Date date;
    private int duration;
    private String eid;
    private String pid;
}
