package lk.ijse.eCounselling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentMethodDetail implements Serializable {
    private String id;
    private String mid;
    private int duration;
}
