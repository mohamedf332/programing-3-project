package com.solt.jdc.service;

import com.solt.jdc.utili.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.solt.jdc.entity.Course;
import com.solt.jdc.utili.DatabaseManager;

public class CourseService {

	public int add(Course course) {
		if (!Validator.isValidCourseName(course.getName()))
			throw new IllegalArgumentException("Invalid course name");

		if (!Validator.isValidDuration(course.getDuration()))
			throw new IllegalArgumentException("Invalid duration");

		if (!Validator.isValidFees(course.getFees()))
			throw new IllegalArgumentException("Invalid fees");
		int num = 0;
		String sql = "insert into course "
				+ "(name,duration,fees,remark) "
				+ "values (?,?,?,?)";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDuration());
			stmt.setInt(3, course.getFees());
			stmt.setString(4, course.getRemark());
			num = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public int deleteById(int id) {
		String sql = "DELETE FROM course WHERE id = ?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, id);
			return stmt.executeUpdate(); // 1 لو اتحذف، 0 لو لا

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Course course) {
		if (!Validator.isValidCourseName(course.getName()))
			throw new IllegalArgumentException("Invalid course name");

		if (!Validator.isValidDuration(course.getDuration()))
			throw new IllegalArgumentException("Invalid duration");

		if (!Validator.isValidFees(course.getFees()))
			throw new IllegalArgumentException("Invalid fees");
		String sql = "UPDATE course SET name=?, duration=?, fees=?, remark=? WHERE id=?";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDuration());
			stmt.setInt(3, course.getFees());
			stmt.setString(4, course.getRemark());
			stmt.setInt(5, course.getId());

			return stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Course> findAll() {
		List<Course> list = new ArrayList<Course>();
		String sql = "Select * from course";
		try (Connection con = DatabaseManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setDuration(rs.getString("duration"));
				course.setFees(rs.getInt("fees"));
				course.setRemark(rs.getString("remark"));
				list.add(course);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
