package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.TreatmentDescDAO;
import lk.ijse.eCounselling.dto.TreatmentDescDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentDescDAOImpl implements TreatmentDescDAO {
    @Override
    public ArrayList<TreatmentDescDTO> getAll() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT t.treat_id,tm.treatm_id, t.treat_status, td.treat_duration ,t.pa_id FROM Treatment t JOIN TreatmentDetail td ON t.treat_id = td.treat_id JOIN TreatmentMethod tm ON td.treatm_id = tm.treatm_id");
        ArrayList<TreatmentDescDTO> treatmentDescList = new ArrayList<>();
        while (resultSet.next()) {
            /*String id = resultSet.getString("t.treat_id");
            String mid = resultSet.getString("tm.treatm_id");
            String status = resultSet.getString("t.treat_status");
            int duration = resultSet.getInt("td.treat_duration");
            String pid = resultSet.getString("t.pa_id");*/
            treatmentDescList.add(new TreatmentDescDTO(resultSet.getString("treat_id"),resultSet.getString("treatm_id"),resultSet.getString("treat_status"),resultSet.getInt("treat_duration"),resultSet.getString("pa_id")));
        }
        return treatmentDescList;
    }
}

