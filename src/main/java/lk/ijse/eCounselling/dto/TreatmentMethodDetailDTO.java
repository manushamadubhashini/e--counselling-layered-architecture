package lk.ijse.eCounselling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentMethodDetailDTO implements Serializable {
    private String id;
    private String mid;
    private int duration;
}
