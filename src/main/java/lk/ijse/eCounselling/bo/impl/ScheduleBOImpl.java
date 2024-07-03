package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.ScheduleBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.ScheduleDAO;
import lk.ijse.eCounselling.dao.impl.ScheduleDAOImpl;
import lk.ijse.eCounselling.dto.ScheduleDTO;
import lk.ijse.eCounselling.entity.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;

public class ScheduleBOImpl implements ScheduleBO {

    ScheduleDAO scheduleDAO= (ScheduleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SCHEDULE);
    @Override
    public boolean save(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.save(new Schedule(dto.getId(),dto.getDate(),dto.getStime(),dto.getEtime(),dto.getEid()));
    }

    @Override
    public boolean update(ScheduleDTO dto) throws SQLException {
        return scheduleDAO.update(new Schedule(dto.getId(),dto.getDate(),dto.getStime(),dto.getEtime(),dto.getEid()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return scheduleDAO.delete(id);
    }

    @Override
    public ArrayList<ScheduleDTO> getAll() throws SQLException {
        ArrayList<ScheduleDTO> getAllSchedule=new ArrayList<>();
        ArrayList<Schedule> schedules=scheduleDAO.getAll();
        for (Schedule s:schedules){
            getAllSchedule.add(new ScheduleDTO(s.getId(),s.getDate(),s.getStime(),s.getEtime(),s.getEid()));
        }
        return getAllSchedule;
    }

    @Override
    public String generateId() throws SQLException {
        return scheduleDAO.generateId();
    }
}
