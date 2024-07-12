package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.PatientBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.PatientDAO;
import lk.ijse.eCounselling.dto.PatientDTO;
import lk.ijse.eCounselling.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO= (PatientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PATIENT);
    @Override
    public boolean save(PatientDTO dto) throws SQLException {
        return patientDAO.save(new Patient(dto.getId(),dto.getName(), dto.getDob(),dto.getAddress(),dto.getContact(),dto.getStatus()));
    }

    @Override
    public boolean update(PatientDTO dto) throws SQLException {
        return patientDAO.update(new Patient(dto.getId(),dto.getName(), dto.getDob(),dto.getAddress(),dto.getContact(),dto.getStatus()));

    }
    @Override
    public boolean delete(String id) throws SQLException {
       return patientDAO.delete(id);
    }

    @Override
    public String generateId() throws SQLException {
        return patientDAO.generateId();
    }
    @Override
    public List<String> getIds() throws SQLException{
        return patientDAO.getIds();
    }
    @Override
    public ArrayList<PatientDTO> getAll() throws SQLException{
        ArrayList<Patient> patients=patientDAO.getAll();
        ArrayList<PatientDTO> patientDTOS=new ArrayList<>();
        for (Patient p:patients){
            patientDTOS.add(new PatientDTO(p.getId(),p.getName(),p.getDob(),p.getAddress(),p.getContact(),p.getStatus()));
        }
        return  patientDTOS;
    }
}
