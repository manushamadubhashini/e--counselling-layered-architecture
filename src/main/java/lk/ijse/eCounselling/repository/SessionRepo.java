package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.SessionDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SessionRepo {
    public static boolean save(SessionDTO session) throws SQLException {
        String sql = "INSERT INTO Session (ses_id, ses_type, ses_date, ses_duration,emp_id,pa_id) VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, session.getId());
        pstm.setObject(2, session.getType());
        pstm.setObject(3, session.getDate());
        pstm.setObject(4, session.getDuration());
        pstm.setObject(5, session.getEid());
        pstm.setObject(6,session.getPid());
        //pstm.setObject(8,employee.getUser_id());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(SessionDTO session) throws SQLException {
        String sql = "UPDATE Session SET  ses_type = ?, ses_date = ?,ses_duration= ?,emp_id=?,pa_id=?  WHERE ses_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, session.getType());
        pstm.setObject(2, session.getDate());
        pstm.setObject(3, session.getDuration());
        pstm.setObject(4,session.getEid());
        pstm.setObject(5,session.getPid());
        pstm.setObject(6,session.getId());
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Session WHERE ses_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<SessionDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM Session";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<SessionDTO> sessionList = new ArrayList<>();
        while (resultSet.next()) {
            sessionList.add(new SessionDTO(resultSet.getString("ses_id"),resultSet.getString("ses_type"), resultSet.getDate("ses_date").toLocalDate(),resultSet.getInt("ses_duration"),resultSet.getString("emp_id"),resultSet.getString("pa_id")));
        }
        return sessionList;
    }
    public static String generateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT ses_id  FROM Session ORDER BY ses_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ses_id");
            int newSessionId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S%03d", newSessionId);
        } else {
            return "S001";
        }
    }

}
