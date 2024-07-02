package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.dto.Session;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SessionRepo {
    public static boolean save(Session session) throws SQLException {
        String sql = "INSERT INTO sessions (ses_id, ses_type, ses_date, ses_duration,emp_id,pa_id) VALUES(?, ?, ?, ?,?,?)";
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
    public static boolean update(String id, String type, LocalDate date, int duration, String eid, String pid) throws SQLException {
        String sql = "UPDATE sessions SET  ses_type = ?, ses_date = ?,ses_duration= ?,emp_id=?,pa_id=?  WHERE ses_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, type);
        pstm.setObject(2, date);
        pstm.setObject(3, duration);
        pstm.setObject(4,eid);
        pstm.setObject(5,pid);
        pstm.setObject(6,id);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM sessions WHERE ses_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static ArrayList<Session> getAll() throws SQLException {
        String sql = "SELECT * FROM sessions";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Session> sessionList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            int duration = Integer.parseInt(resultSet.getString(4));
            String eid=resultSet.getString(5);
            String pid=resultSet.getString(6);

            Session session = new Session(id,type, date.toLocalDate(),duration,eid,pid);
            sessionList.add(session);
        }
        return sessionList;
    }
    public static String generateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT ses_id  FROM sessions ORDER BY ses_id  DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ses_id");
            int newSessionId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S%03d", newSessionId);
        } else {
            return "S001";
        }
    }

}
