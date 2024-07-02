package lk.ijse.eCounselling.dto.tm;

import lk.ijse.eCounselling.dto.Treatment;
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
