package lk.ijse.eCounselling.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentDescTm  {
    private String id;
    private String mid;
    private String status;
    private String description;
    private int duration;
    private String pid;
}
