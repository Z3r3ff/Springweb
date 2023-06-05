package com.btl2.bookstore.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookOrderDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/btl2_ltw";
	private String jdbcUsername = "root";
	private String jdbcPass = "Anhk0biet";
	
	private static final String INSERT_TO_CART = "INSERT INTO bookorder (idbook, iduser, checkout, quantity) VALUES (?,?,?,?)";
	private static final String REMOVE_FROM_CART = "DELETE FROM bookorder WHERE idbookorder=? and iduser=? and checkout=?";
	private static final String CHECK_IF_EXIST = "SELECT * FROM bookorder WHERE checkout = ? and iduser = ? and idbook = ?";
	private static final String UPDATE_QUANTITY = "UPDATE bookorder SET quantity = ? WHERE idbookorder = ?";
	private static final String GET_ALL_CARTITEM = "SELECT * FROM bookorder WHERE checkout = ? and iduser = ?";
	private static final String REMOVE_CHECKOUT = "DELETE FROM bookorder WHERE iduser = ? and checkout = ?";
	
	public BookOrderDAO() {
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
	
	public void removeCheckOutItem(int iduser, int idcheckout) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(REMOVE_CHECKOUT);
			ps.setInt(1, iduser);
			ps.setInt(2, idcheckout);
			ps.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void removeItem(int iduser, int idbookorder) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(REMOVE_FROM_CART);
			ps.setInt(1, idbookorder);
			ps.setInt(2, iduser);
			ps.setInt(3, 0);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void AddToCart(BookOrder bookOrder) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps1 = connection.prepareStatement(CHECK_IF_EXIST);
			ps1.setInt(1, 0);
			ps1.setInt(2, bookOrder.getIduser());
			ps1.setInt(3, bookOrder.getIdbook());
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				PreparedStatement ps = connection.prepareStatement(UPDATE_QUANTITY);
				int newquantity = rs1.getInt("quantity");
				newquantity += bookOrder.getQuantity();
				ps.setInt(1, newquantity);
				ps.setInt(2, rs1.getInt("idbookorder"));
				ps.executeUpdate();
			}
			else {
				PreparedStatement ps = connection.prepareStatement(INSERT_TO_CART);
				ps.setInt(1, bookOrder.getIdbook());
				ps.setInt(2, bookOrder.getIduser());
				ps.setInt(3, bookOrder.getCheckout());
				ps.setInt(4, bookOrder.getQuantity());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<BookOrder> GetAllItemCart(int id){
		List<BookOrder> list = new ArrayList<BookOrder>();
		try {
			BookDAO bookDAO = new BookDAO();
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ALL_CARTITEM);
			ps.setInt(1, 0);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			BookOrder bookOrder;
			while (rs.next()) {
				int idbookorder = rs.getInt("idbookorder");
				int idbook = rs.getInt("idbook");
				int iduser = rs.getInt("iduser");
				int checkout = rs.getInt("checkout");
				int quantity = rs.getInt("quantity");
				bookOrder = new BookOrder(idbookorder, idbook, iduser, checkout, quantity);
				bookOrder.setBook(bookDAO.getBook(idbook));
				list.add(bookOrder);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BookOrder> GetAllCheckOutItem(int iduser,int idcheckout) {
		List<BookOrder> list = new ArrayList<BookOrder>();
		try {
			BookDAO bookDAO = new BookDAO();
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ALL_CARTITEM);
			ps.setInt(1, idcheckout);
			ps.setInt(2, iduser);
			ResultSet rs = ps.executeQuery();
			BookOrder bookOrder;
			while (rs.next()) {
				int idbookorder = rs.getInt("idbookorder");
				int idbook = rs.getInt("idbook");
				int idusr = rs.getInt("iduser");
				int checkout = rs.getInt("checkout");
				int quantity = rs.getInt("quantity");
				bookOrder = new BookOrder(idbookorder, idbook, idusr, checkout, quantity);
				bookOrder.setBook(bookDAO.getBook(idbook));
				list.add(bookOrder);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
}
