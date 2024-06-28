package lk.ijse.eCounselling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentDesc {
    private String id;
    private String mid;
    private String status;
    private int duration;
    private String pid;
}
