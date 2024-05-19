package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Employee;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee ( emp_id ,  emp_name , emp_DOB  ,  emp_address, emp_contact, emp_position, emp_joinDate,user_id) VALUES(?, ?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getDOB());
        pstm.setObject(4, employee.getAddress());
        pstm.setObject(5, employee.getContact());
        pstm.setObject(6, employee.getPosition());
        pstm.setObject(7, employee.getJoinDate());
        pstm.setObject(8,employee.getUid());
        //pstm.setObject(8,employee.getUser_id());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String id, String name, LocalDate DOB,String address,String contact,String position,LocalDate joinDate,String uid) throws SQLException {
        String sql = "UPDATE employee SET emp_name = ?, emp_DOB = ?, emp_address = ?,emp_contact= ?,emp_position= ?,emp_joinDate= ?,user_id=? WHERE emp_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, name);
        pstm.setObject(2, DOB);
        pstm.setObject(3, address);
        pstm.setObject(4, contact);
        pstm.setObject(5, position);
        pstm.setObject(6, joinDate);
        pstm.setObject(7,uid);
        pstm.setObject(8,id);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            Date dob = resultSet.getDate(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);
            String position=resultSet.getString(6);
            Date joinDate = resultSet.getDate(7);
            String uid=resultSet.getString(8);

            Employee employee = new Employee(id, name,dob,address,contact,position,joinDate,uid);
            employeeList.add(employee);
        }
        return employeeList;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT emp_id FROM employee";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }



}

