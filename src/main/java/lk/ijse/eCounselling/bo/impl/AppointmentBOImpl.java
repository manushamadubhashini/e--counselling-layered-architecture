package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.AppointmentBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.AppointmentDAO;
import lk.ijse.eCounselling.dto.AppointmentDTO;
import lk.ijse.eCounselling.entity.Appointment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentBOImpl implements AppointmentBO {

    AppointmentDAO appointmentDAO= (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.APPOINTMENT);
    @Override
    public ArrayList<AppointmentDTO> getAll() throws SQLException {
        ArrayList<AppointmentDTO> all=new ArrayList<>();
        ArrayList<Appointment> appointments=appointmentDAO.getAll();
        for (Appointment a:appointments){
            all.add(new AppointmentDTO(a.getId(),a.getType(),a.getDate(),a.getTime(),a.getEid(),a.getPid()));
        }
        return all;
    }

    @Override
    public boolean save(AppointmentDTO dto) throws SQLException {
        return appointmentDAO.save(new Appointment(dto.getId(),dto.getType(),dto.getDate(),dto.getTime(),dto.getEid(),dto.getPid()));
    }

    @Override
    public boolean update(AppointmentDTO dto) throws SQLException {
        return appointmentDAO.update(new Appointment(dto.getId(),dto.getType(),dto.getDate(),dto.getTime(),dto.getEid(),dto.getPid()));
    }

    @Override
    public String generateId() throws SQLException {
        return  appointmentDAO.generateId();
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return appointmentDAO.delete(id);
    }
}
