package com.btl2.bookstore.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CheckOutDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/btl2_ltw";
	private String jdbcUsername = "root";
	private String jdbcPass = "Anhk0biet";
	
	private static final String INSERT_CHECKOUT = "INSERT INTO checkout(iduser,fullname,addressorder,phonenumber,paymethod,finalprice) VALUES(?,?,?,?,?,?)";
	private static final String DELETE_CHECKOUT = "DELETE FROM checkout where idcheckout = ? and iduser = ?";
	private static final String GET_ALL_CHECKOUT = "SELECT * FROM checkout WHERE iduser = ?";
	private static final String CHECKOUT_ITEM = "UPDATE bookorder SET checkout = ? WHERE iduser = ? and checkout = ?";
	
	public CheckOutDAO() {
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
	
	public void deleteCheckOut(int iduser,int idcheckout) {
		try {
			BookOrderDAO bookOrderDAO = new BookOrderDAO();
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CHECKOUT);
			ps.setInt(1, idcheckout);
			ps.setInt(2, iduser);
			ps.executeUpdate();
			bookOrderDAO.removeCheckOutItem(iduser, idcheckout);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void insertCheckOut(CheckOut checkOut) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_CHECKOUT);
			ps.setInt(1, checkOut.getIduser());
			ps.setString(2, checkOut.getFullname());
			ps.setString(3, checkOut.getAddressorder());
			ps.setString(4, checkOut.getPhonenumber());
			ps.setString(5, checkOut.getPaymethod());
			ps.setFloat(6, checkOut.getFinalprice());
			ps.executeUpdate();
			String sql2 = "select max(idcheckout) from checkout";
			PreparedStatement ps1 = connection.prepareStatement(sql2);
			ResultSet rs = ps1.executeQuery();
			int idcheckout = 0;
			if (rs.next()) {
				idcheckout = rs.getInt(1);
			}
			ps = connection.prepareStatement(CHECKOUT_ITEM);
			ps.setInt(1, idcheckout);
			ps.setInt(2, checkOut.getIduser());
			ps.setInt(3, 0);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<CheckOut> getCheckOuts(int iduser) {
		List<CheckOut> checkoutlist = new ArrayList<CheckOut>();
		BookOrderDAO bookOrderDAO = new BookOrderDAO();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ALL_CHECKOUT);
			ps.setInt(1, iduser);
			ResultSet rs = ps.executeQuery();
			CheckOut checkOut;
			while (rs.next()) {
				int idcheckout = rs.getInt("idcheckout");
				int idusr = rs.getInt("iduser");
				String fullname = rs.getString("fullname");
				String addressorder = rs.getString("addressorder");
				String phonenumber = rs.getString("phonenumber");
				String paymethod = rs.getString("paymethod");
				float finalprice = rs.getFloat("finalprice");
				List<BookOrder> list = bookOrderDAO.GetAllCheckOutItem(idusr, idcheckout);
				checkOut = new CheckOut(idcheckout, idusr, fullname, addressorder, phonenumber, paymethod, finalprice);
				checkOut.setBooks(list);
				checkoutlist.add(checkOut);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return checkoutlist;
	}
}
