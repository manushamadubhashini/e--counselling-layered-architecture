package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.TreatmentMethodBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.TreatmentMethodDAO;
import lk.ijse.eCounselling.dto.TreatmentMethodDTO;
import lk.ijse.eCounselling.entity.TreatmentMethod;

import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentMethodBOImpl implements TreatmentMethodBO {

    TreatmentMethodDAO treatmentMethodDAO= (TreatmentMethodDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.TREATMENTMETHOD);

    @Override
    public boolean save(TreatmentMethodDTO entity) throws SQLException {
        return treatmentMethodDAO.save(new TreatmentMethod(entity.getMid(),entity.getDescription()));
    }

    @Override
    public boolean update(TreatmentMethodDTO entity) throws SQLException {
        return treatmentMethodDAO.update(new TreatmentMethod(entity.getMid(),entity.getDescription()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return treatmentMethodDAO.delete(id);
    }

    @Override
    public ArrayList<TreatmentMethodDTO> getAll() throws SQLException {
        ArrayList<TreatmentMethod> treatmentMethodArrayList=treatmentMethodDAO.getAll();
        ArrayList<TreatmentMethodDTO> getAllTreatmentMethod=new ArrayList<>();
        for (TreatmentMethod m:treatmentMethodArrayList){
            getAllTreatmentMethod.add(new TreatmentMethodDTO(m.getMid(),m.getDescription()));
        }
        return getAllTreatmentMethod;
    }

    @Override
    public String generateId() throws SQLException {
        return treatmentMethodDAO.generateId();
    }
}
