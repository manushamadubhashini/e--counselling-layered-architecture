package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.EmployeeDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeRepo {
    public static boolean save(EmployeeDTO employee) throws SQLException {
        String sql = "INSERT INTO Employee ( emp_id ,  emp_name , emp_DOB  ,  emp_address, emp_contact, emp_position, emp_joinDate,user_id) VALUES(?, ?, ?, ?, ?,?,?,?)";
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
    public static boolean update(String id, String name, String DOB,String address,String contact,String position,LocalDate joinDate,String uid) throws SQLException {
        String sql = "UPDATE Employee SET emp_name = ?, emp_DOB = ?, emp_address = ?,emp_contact= ?,emp_position= ?,emp_joinDate= ?,user_id=? WHERE emp_id = ?";

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
        String sql = "DELETE FROM Employee WHERE emp_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<EmployeeDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

       ArrayList<EmployeeDTO> employeeList=new ArrayList<>();
        while (resultSet.next()) {
            EmployeeDTO employee=new EmployeeDTO(resultSet.getString("emp_id"),resultSet.getString("emp_name"),resultSet.getString("emp_DOB"),resultSet.getString("emp_address"),resultSet.getString("emp_contact"),resultSet.getString("emp_position"), resultSet.getDate("emp_joinDate").toLocalDate(),resultSet.getString("user_id"));
            employeeList.add(employee);
        }
        return employeeList;
    }
    public static String generateId() throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();
        Statement stm=connection.createStatement();
        ResultSet rst=stm.executeQuery("SELECT emp_id  FROM Employee ORDER BY emp_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("emp_id");
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E%03d",newEmployeeId);
        } else {
            return "E001";
        }
    }



}

