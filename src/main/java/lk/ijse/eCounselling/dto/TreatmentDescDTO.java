package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentDescDTO {
    private String id;
    private String mid;
    private String status;
    private int duration;
    private String pid;
}
