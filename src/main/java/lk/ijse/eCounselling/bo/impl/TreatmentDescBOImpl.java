package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.TreatmentDescBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.TreatmentDescDAO;
import lk.ijse.eCounselling.dto.TreatmentDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentDescBOImpl implements TreatmentDescBO {

    TreatmentDescDAO treatmentDescDAO= (TreatmentDescDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.TREATMENTDESC);
    @Override
    public ArrayList<TreatmentDescDTO> getAll() throws SQLException {
        return treatmentDescDAO.getAll();

    }
}
