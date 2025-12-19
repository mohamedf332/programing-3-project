package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entity.Registration;
import util.DatabaseManager;

public class RegistrationService {

    public int add(Registration reg) {
        String sql = "INSERT INTO registration (fees, paid, student_id, class_id, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseManager.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, reg.getFees());
            stmt.setInt(2, reg.getPaid());
            stmt.setInt(3, reg.getStudentId());
            stmt.setInt(4, reg.getClassId()); // Using Course ID as Class ID for simplicity
            stmt.setDate(5, Date.valueOf(LocalDate.now()));

            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Registration> findAll() {
        List<Registration> list = new ArrayList<>();
        // Joining tables to get names
        String sql = "SELECT r.*, s.name as student_name, c.name as course_name " +
                "FROM registration r " +
                "JOIN student s ON r.student_id = s.id " +
                "JOIN course c ON r.class_id = c.id";

        try (Connection con = DatabaseManager.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Registration reg = new Registration();
                reg.setId(rs.getInt("id"));
                reg.setFees(rs.getInt("fees"));
                reg.setPaid(rs.getInt("paid"));
                reg.setStudentId(rs.getInt("student_id"));
                reg.setClassId(rs.getInt("class_id"));

                if (rs.getDate("date") != null) {
                    reg.setStartDate(rs.getDate("date").toLocalDate());
                }

                // Set names for display
                reg.setStudentName(rs.getString("student_name"));
                reg.setClassName(rs.getString("course_name")); // Reusing className for courseName

                list.add(reg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
