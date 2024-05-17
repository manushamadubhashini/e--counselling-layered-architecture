package lk.ijse.eCounselling.repository;

import lk.ijse.eCounselling.db.DbConnection;
import lk.ijse.eCounselling.model.Appointment;
import lk.ijse.eCounselling.model.Report;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportRepo {
    public static boolean save(Report report) throws SQLException {
        String sql = "INSERT INTO report (rep_id,gender,description, pa_id) VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, report.getRid());
        pstm.setObject(2, report.getGender());
        pstm.setObject(3, report.getDes());
        pstm.setObject(4, report.getId());
        return pstm.executeUpdate() > 0;


    }
    public static boolean update(String rid, String gender, String desc,String id) throws SQLException {
        String sql = "UPDATE report SET gender = ?,description = ?,pa_id = ? WHERE rep_id  = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, gender);
        pstm.setObject(2, desc);
        pstm.setObject(3,id );
        pstm.setObject(4, rid);
        return pstm.executeUpdate() > 0;



    }
    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM report WHERE rep_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Report> getAll() throws SQLException {
        String sql = "SELECT * FROM report";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Report> reportList = new ArrayList<>();
        while (resultSet.next()) {
            String rid = resultSet.getString(1);
            String gender = resultSet.getString(2);
            String desc = resultSet.getString(4);
            String id = resultSet.getString(4);

            Report report = new Report(rid, gender,desc,id);
            reportList.add(report);
        }
        return reportList;
    }



}
