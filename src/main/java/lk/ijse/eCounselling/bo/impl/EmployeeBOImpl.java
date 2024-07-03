package lk.ijse.eCounselling.bo.impl;

import lk.ijse.eCounselling.bo.custom.AppointmentBO;
import lk.ijse.eCounselling.bo.custom.EmployeeBO;
import lk.ijse.eCounselling.dao.DAOFactory;
import lk.ijse.eCounselling.dao.custom.AppointmentDAO;
import lk.ijse.eCounselling.dao.custom.EmployeeDAO;
import lk.ijse.eCounselling.dao.impl.AppointmentDAOImpl;
import lk.ijse.eCounselling.dao.impl.EmployeeDAOImpl;
import lk.ijse.eCounselling.dto.EmployeeDTO;
import lk.ijse.eCounselling.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO= (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);
    @Override
    public boolean save(EmployeeDTO dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getId(), dto.getName(), dto.getDOB(), dto.getAddress(),dto.getContact(), dto.getPosition(),dto.getJoinDate(), dto.getUid()));
    }

    @Override
    public boolean update(EmployeeDTO dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getName(),dto.getDOB(),dto.getAddress(),dto.getContact(),dto.getPosition(),dto.getJoinDate(),dto.getUid()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException {
        ArrayList<Employee> employees=employeeDAO.getAll();
        ArrayList<EmployeeDTO> getAllEmployee= new ArrayList<>();
        for (Employee e:employees){
            getAllEmployee.add(new EmployeeDTO(e.getId(),e.getName(),e.getDOB(),e.getAddress(),e.getContact(),e.getPosition(),e.getJoinDate(),e.getUid()));

        }
        return getAllEmployee;
    }

    @Override
    public String generateId() throws SQLException {
        return employeeDAO.generateId();
    }
}
