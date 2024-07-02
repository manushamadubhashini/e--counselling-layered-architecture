package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.AppointmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AppointmentBO extends SuperBO {
    public ArrayList<AppointmentDTO> getAll() throws SQLException;

    public boolean save(AppointmentDTO dto) throws SQLException;

    public boolean update(AppointmentDTO dto) throws SQLException;

    public String generateId() throws SQLException;
    
    public boolean delete(String id) throws SQLException;

}
