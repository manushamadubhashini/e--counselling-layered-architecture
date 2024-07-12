package lk.ijse.eCounselling.bo.impl;

import javafx.scene.control.Alert;
import lk.ijse.eCounselling.bo.custom.TreatmentBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.TreatmentDAO;
import lk.ijse.eCounselling.dao.custom.TreatmentMethodDetailDAO;
import lk.ijse.eCounselling.dao.impl.TreatmentDAOImpl;
import lk.ijse.eCounselling.dao.impl.TreatmentMethodDetailDAOImpl;
import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.TreatmentDTO;
import lk.ijse.eCounselling.dto.TreatmentMethodDetailDTO;
import lk.ijse.eCounselling.dto.tm.TreatmentDescTm;
import lk.ijse.eCounselling.entity.Treatment;
import lk.ijse.eCounselling.entity.TreatmentMethod;
import lk.ijse.eCounselling.entity.TreatmentMethodDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentBOImpl implements TreatmentBO {
    TreatmentDAO treatmentDAO=(TreatmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.TREATMENT);
    TreatmentMethodDetailDAO treatmentMethodDetailDAO=(TreatmentMethodDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.TREATMENTDETAIL);

    @Override
    public ArrayList<TreatmentMethod> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean TreatmentSave(TreatmentDTO entity) throws SQLException {
        Connection connection=null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isTreatmentSaved = treatmentDAO.save(new Treatment(entity.getId(),entity.getStatus(),entity.getPid()));
            if (!isTreatmentSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            for (TreatmentMethodDetailDTO d: entity.getTreatmentMethodDetail()){
                boolean isDetailSaved=treatmentMethodDetailDAO.save(new TreatmentMethodDetail(d.getId(),d.getMid(),d.getDuration()));
                if (!isDetailSaved) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;

    }

    @Override
    public boolean TreatmentUpdate(TreatmentDTO entity) throws SQLException {
        Connection connection=null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isTreatmentUpdated = treatmentDAO.update(new Treatment(entity.getId(),entity.getStatus(),entity.getPid()));
            if(! isTreatmentUpdated){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            for (TreatmentMethodDetailDTO d:entity.getTreatmentMethodDetail()) {
                boolean isDetailUpdated = treatmentMethodDetailDAO.update(new TreatmentMethodDetail(d.getId(),d.getMid(),d.getDuration()));
                if (!isDetailUpdated) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean TreatmentDelete(String id) throws SQLException {
        Connection connection=null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isDetailDeleted=treatmentMethodDetailDAO.delete(id);
            if(!isDetailDeleted){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            boolean isTreatmentDeleted = treatmentDAO.delete(id);
            if(!isTreatmentDeleted){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;


    }
    @Override
    public String generateId() throws SQLException {
        return treatmentDAO.generateId();

    }


}
