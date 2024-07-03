package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.SessionBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.SessionDAO;
import lk.ijse.eCounselling.dto.SessionDTO;
import lk.ijse.eCounselling.entity.Session;

import java.sql.SQLException;
import java.util.ArrayList;

public class SessionBoImpl implements SessionBO {

    SessionDAO sessionDAO= (SessionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SESSION);
    @Override
    public boolean save(SessionDTO entity) throws SQLException {
        return sessionDAO.save(new Session(entity.getId(), entity.getType(),entity.getDate(), entity.getDuration(), entity.getEid(), entity.getPid()));
    }

    @Override
    public boolean update(SessionDTO entity) throws SQLException {
        return sessionDAO.update(new Session(entity.getId(), entity.getType(),entity.getDate(), entity.getDuration(), entity.getEid(), entity.getPid()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return sessionDAO.delete(id);
    }

    @Override
    public ArrayList<SessionDTO> getAll() throws SQLException {
        ArrayList<Session> sessions=sessionDAO.getAll();
        ArrayList<SessionDTO> getAllSession=new ArrayList<>();
        for(Session s:sessions){
            getAllSession.add(new SessionDTO(s.getId(),s.getType(),s.getDate(),s.getDuration(),s.getEid(),s.getPid()));
        }
        return  getAllSession;
    }

    @Override
    public String generateId() throws SQLException {
        return sessionDAO.generateId();
    }
}
