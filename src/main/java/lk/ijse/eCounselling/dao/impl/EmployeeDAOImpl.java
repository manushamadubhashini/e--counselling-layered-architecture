package lk.ijse.eCounselling.dao.impl;

import lk.ijse.eCounselling.dao.SQLUtil;
import lk.ijse.eCounselling.dao.custom.EmployeeDAO;
import lk.ijse.eCounselling.dto.EmployeeDTO;
import lk.ijse.eCounselling.entity.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Employee (emp_id , emp_name ,emp_DOB ,emp_address, emp_contact, emp_position, emp_joinDate,user_id) VALUES(?, ?, ?, ?, ?,?,?,?)",entity.getId(),entity.getName(),entity.getDOB(),entity.getAddress(),entity.getContact(),entity.getPosition(),entity.getJoinDate(),entity.getUid());
    }
    @Override
    public  boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE Employee SET emp_name = ?, emp_DOB = ?, emp_address = ?,emp_contact= ?,emp_position= ?,emp_joinDate= ?,user_id=? WHERE emp_id = ?",entity.getName(),entity.getDOB(),entity.getAddress(),entity.getContact(),entity.getPosition(),entity.getJoinDate(),entity.getUid(),entity.getId());

    }
    @Override
    public  boolean delete(String id) throws SQLException {
        return SQLUtil.execute( "DELETE FROM Employee WHERE emp_id = ?",id);
    }
    @Override
    public  ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> employeeList=new ArrayList<>();
        ResultSet resultSet=SQLUtil.execute("SELECT * FROM Employee");
        while (resultSet.next()) {
            Employee employee=new Employee(resultSet.getString("emp_id"),resultSet.getString("emp_name"),resultSet.getString("emp_DOB"),resultSet.getString("emp_address"),resultSet.getString("emp_contact"),resultSet.getString("emp_position"), resultSet.getDate("emp_joinDate").toLocalDate(),resultSet.getString("user_id"));
            employeeList.add(employee);
        }
        return employeeList;
    }
    @Override
    public  String generateId() throws SQLException {
        ResultSet rst=SQLUtil.execute("SELECT emp_id  FROM Employee ORDER BY emp_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("emp_id");
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E%03d",newEmployeeId);
        } else {
            return "E001";
        }
    }
}


