package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.PatientDescBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.PatientDescDAO;
import lk.ijse.eCounselling.dto.PatientDescDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDescBOImpl implements PatientDescBO {

    PatientDescDAO patientDescDAO= (PatientDescDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PATIENTREPORTDESC);
    @Override
    public ArrayList<PatientDescDTO> getAll() throws SQLException {
        return patientDescDAO.getAll();

    }
}
