package lk.ijse.eCounselling.entity;

import lk.ijse.eCounselling.dto.TreatmentMethodDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Treatment {
    private String id;
    private String status;
    private String pid;

    List<TreatmentMethodDetailDTO> treatmentMethodDetailDTOList;

    public List<TreatmentMethodDetailDTO> getTreatmentMethodDetail(){
        return treatmentMethodDetailDTOList;
    }

    public Treatment(String id, String status, String pid){
        this.id=id;
        this.status=status;
        this.pid=pid;
    }

}
