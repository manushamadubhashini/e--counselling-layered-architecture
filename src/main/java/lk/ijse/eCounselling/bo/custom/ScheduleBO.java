package lk.ijse.eCounselling.bo.custom;

import lk.ijse.eCounselling.bo.SuperBO;
import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dto.ScheduleDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleBO extends SuperBO {
    public  boolean save(ScheduleDTO dto) throws SQLException;

    public  boolean update(ScheduleDTO dto) throws SQLException;

    public  boolean delete(String id) throws SQLException;
    public  ArrayList<ScheduleDTO> getAll() throws SQLException;
    public  String generateId() throws SQLException;

}
