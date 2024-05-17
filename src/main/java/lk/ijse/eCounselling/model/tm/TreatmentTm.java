package lk.ijse.eCounselling.model.tm;

import lk.ijse.eCounselling.model.Treatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentTm extends Treatment {
    private String id;
    private String status;
}
