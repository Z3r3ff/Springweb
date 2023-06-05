package com.btl2.bookstore.book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;



public class BookDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/btl2_ltw";
	private String jdbcUsername = "root";
	private String jdbcPass = "Anhk0biet";
	
	private static final String SELECT_ALL_BOOK = "select * from book left join bookcover on book.idbook = bookcover.idbook";
	private static final String SELECT_BOOK_BY_CATEGORY = "select * from book left join bookcover on book.idbook = bookcover.idbook where book.category =?";
	private static final String SELECT_BOOK_ID = "select * from book left join bookcover on book.idbook = bookcover.idbook where book.idbook = ?";
	private static final String INSERT_BOOK = "INSERT INTO book (title, author, description, pubDate, totalPage, price, category) VALUES(?,?,?,?,?,?,?)";
	private static final String INSERT_BOOK_COVER = "INSERT INTO bookcover (idbook, cover) VALUES(?,?)";
	private static final String UPDATE_BOOK = "UPDATE book SET title=?,author=?,description=?,pubDate=?,totalPage=?,price=?,category=? WHERE idbook=?";
	private static final String UPDATE_BOOK_COVER = "UPDATE bookcover SET cover=? WHERE idbook=?";
	private static final String DELETE_BOOK = "DELETE FROM book WHERE idbook=?";
	private static final String DELETE_BOOKCOVER = "DELETE FROM bookcover WHERE idbook=?";
	
	public BookDAO() {
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
	
	public List<Book> searchBook(String name,String category2){
		List<Book> results = new ArrayList<Book>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = null;
			if (category2.equals("All")) {
				ps = connection.prepareStatement(SELECT_ALL_BOOK);
			}
			else {
				ps = connection.prepareStatement(SELECT_BOOK_BY_CATEGORY);
				ps.setString(1, category2);
			}
			ResultSet resultSet = ps.executeQuery();
			Book book;
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				if (title.contains(name)) {
					int idbook = resultSet.getInt("idbook");
					String author = resultSet.getString("author");
					String description = resultSet.getString("description");
					int totalPage = resultSet.getInt("totalPage");
					float price = resultSet.getFloat("price");
					String category = resultSet.getString("category");
					String cover = resultSet.getString("cover");
					Date pubDate = resultSet.getDate("pubDate");
					book = new Book(idbook, title, author, description, totalPage, price, category, cover, pubDate);
					results.add(book);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Book> selectBookByCategory(String category1){
		List<Book> results = new ArrayList<Book>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOK_BY_CATEGORY);
			ps.setString(1, category1);
			ResultSet resultSet = ps.executeQuery();
			Book book;
			while (resultSet.next()) {
				int idbook = resultSet.getInt("idbook");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				int totalPage = resultSet.getInt("totalPage");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				String cover = resultSet.getString("cover");
				Date pubDate = resultSet.getDate("pubDate");
				book = new Book(idbook, title, author, description, totalPage, price, category, cover, pubDate);
				results.add(book);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Book> selectAllBook() {
		List<Book> results = new ArrayList<Book>();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_BOOK);
			ResultSet resultSet = ps.executeQuery();
			Book book;
			while (resultSet.next()) {
				int idbook = resultSet.getInt("idbook");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				int totalPage = resultSet.getInt("totalPage");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				String cover = resultSet.getString("cover");
				Date pubDate = resultSet.getDate("pubDate");
				book = new Book(idbook, title, author, description, totalPage, price, category, cover,pubDate);
				results.add(book);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}
	
	public Book getBook(int idbook) {
		Book book = new Book();
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_BOOK_ID);
			ps.setInt(1, idbook);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book.setIdbook(rs.getInt("idbook"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setDescription(rs.getString("description"));
				book.setTotalPage(rs.getInt("totalPage"));
				book.setPubDate(rs.getDate("pubDate"));
				book.setCover(rs.getString("cover"));
				book.setCategory(rs.getString("category"));
				book.setPrice(rs.getFloat("price"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return book;
	}
	
	public void insertBook(Book book, MultipartFile file) {
		try {
			String filename = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
			if(filename.contains("..")) {
				System.out.print("Not valid type");
			}
			book.setCover(Base64.getEncoder().encodeToString(file.getBytes()));
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_BOOK);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setDate(4, new java.sql.Date(book.getPubDate().getTime()));
			ps.setInt(5, book.getTotalPage());
			ps.setFloat(6, book.getPrice());
			ps.setString(7, book.getCategory());
			int result = ps.executeUpdate();
			String sql2 = "select max(idbook) from book";
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			ResultSet rs = ps2.executeQuery();
			int newId = 0;
			if(rs.next()) {
				newId = rs.getInt(1);
			}
			ps2.close();
			ps = connection.prepareStatement(INSERT_BOOK_COVER);
			ps.setInt(1, newId);
			ps.setString(2, book.getCover());
			ps.executeUpdate();
			ps.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void updateBook(Book book, MultipartFile file) {
		try {
			String filename = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
			if(filename.contains("..")) {
				System.out.print("Not valid type");
			}
			book.setCover(Base64.getEncoder().encodeToString(file.getBytes()));
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setDate(4, new java.sql.Date(book.getPubDate().getTime()));
			ps.setInt(5, book.getTotalPage());
			ps.setFloat(6, book.getPrice());
			ps.setString(7, book.getCategory());
			ps.setInt(8, book.getIdbook());
			int result = ps.executeUpdate();
			ps = connection.prepareStatement(UPDATE_BOOK_COVER);
			ps.setString(1, book.getCover());
			ps.setInt(2, book.getIdbook());
			ps.executeUpdate();
			ps.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deleteBook(int idbook) {
		try {
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_BOOKCOVER);
			ps.setInt(1, idbook);
			ps.executeUpdate();
			ps = connection.prepareStatement(DELETE_BOOK);
			ps.setInt(1, idbook);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}