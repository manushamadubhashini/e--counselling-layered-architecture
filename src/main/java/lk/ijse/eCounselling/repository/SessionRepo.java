package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Session;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionRepo {
    public static boolean save(Session session) throws SQLException {
        String sql = "INSERT INTO sessions (ses_id, ses_type, ses_date, ses_duration) VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, session.getId());
        pstm.setObject(2, session.getType());
        pstm.setObject(3, session.getDate());
        pstm.setObject(4, session.getDuration());
        //pstm.setObject(8,employee.getUser_id());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String id, String type, java.util.Date date, int duration) throws SQLException {
        String sql = "UPDATE sessions SET  ses_type = ?, ses_date = ?,ses_duration= ? WHERE ses_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, type);
        pstm.setObject(2, date);
        pstm.setObject(3, duration);
        pstm.setObject(4,id);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM sessions WHERE ses_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Session> getAll() throws SQLException {
        String sql = "SELECT * FROM sessions";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Session> sessionList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            Date date = resultSet.getDate(3);
            int duration = Integer.parseInt(resultSet.getString(4));

            Session session = new Session(id,type,date,duration);
            sessionList.add(session);
        }
        return sessionList;
    }

}
