package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.ReportBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.ReportDAO;
import lk.ijse.eCounselling.dto.ReportDTO;
import lk.ijse.eCounselling.entity.Report;

import java.sql.SQLException;
import java.util.List;

public class ReportBOImpl implements ReportBO {

    ReportDAO reportDAO= (ReportDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.REPORT);
    @Override
    public boolean save(ReportDTO dto) throws SQLException {
        return reportDAO.save(new Report(dto.getRid(),dto.getGender(),dto.getDes(), dto.getId()));
    }

    @Override
    public boolean update(ReportDTO dto) throws SQLException {
        return reportDAO.update(new Report(dto.getRid(),dto.getGender(),dto.getDes(), dto.getId()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return reportDAO.delete(id);
    }

    @Override
    public String generateId() throws SQLException {
        return reportDAO.generateId();
    }

}
