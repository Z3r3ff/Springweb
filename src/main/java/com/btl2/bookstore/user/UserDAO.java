package com.btl2.bookstore.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/btl2_ltw";
	private String jdbcUsername = "root";
	private String jdbcPass = "Anhk0biet";
	
	private static final String SELECT_USER_USERNAME = "select * from user where username = ? and password = ?";
	private static final String CHECK_USER_USERNAME = "select * from user where username = ?";
	private static final String INSERT_USER = "INSERT INTO user (username, password, name, age, address, role) VALUES(?,?,?,?,?,?)";
	
	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPass);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
	}
	
	public int getIDUser(String username) {
		int iduser = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CHECK_USER_USERNAME);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				iduser = rs.getInt("iduser");
			}
			return iduser;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return iduser;
	}
	
	public boolean checkExistUser(User user) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(CHECK_USER_USERNAME);
			ps.setString(1, user.getUsername());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	public String checkUserandRole(User user) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_USERNAME);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String role = rs.getString("role");
				return role;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Not Found";
	}
	
	public void addUser(User user) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_USER);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getAge());
			ps.setString(5, user.getAddress());
			ps.setString(6, "customer");
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
