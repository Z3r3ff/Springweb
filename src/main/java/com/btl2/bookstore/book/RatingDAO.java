package com.btl2.bookstore.book;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RatingDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/btl2_ltw";
	private String jdbcUsername = "root";
	private String jdbcPass = "Anhk0biet";
	
	private static final String INSERT_RATING = "INSERT INTO bookrating (idbook, username, rating, review) VALUES(?,?,?,?)";
	private static final String SELECT_ALL_RATING = "SELECT * FROM bookrating WHERE idbook = ?";
	
	
	public RatingDAO() {
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
	
	public void insertRating(Rating rating) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_RATING);
			ps.setInt(1, rating.getIdbook());
			ps.setString(2, rating.getUsername());
			ps.setFloat(3, rating.getStars());
			ps.setString(4, rating.getReview());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<Rating> getAllRating(int idbook) {
		List<Rating> results = new ArrayList<Rating>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_RATING);
			ps.setInt(1, idbook);
			ResultSet rs = ps.executeQuery();
			Rating rating;
			while (rs.next()) {
				String username = rs.getString("username");
				float stars = rs.getFloat("rating");
				String review = rs.getString("review");
				rating = new Rating(idbook, username, stars, review);
				results.add(rating);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}
}
