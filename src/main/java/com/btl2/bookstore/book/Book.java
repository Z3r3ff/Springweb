package com.btl2.bookstore.book;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class Book {
	private int idbook;
	@NotBlank
	private String title;
	@NotBlank
	private String author;
	@NotBlank
	private String description;
	private int totalPage;
	private float price;
	private String category;
	private String cover;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pubDate;

	public Book(int idbook, String title, String author, String description, int totalPage,float price,String category, String cover,
			Date pubDate) {
		super();
		this.idbook = idbook;
		this.title = title;
		this.author = author;
		this.description = description;
		this.totalPage = totalPage;
		this.category = category;
		this.price = price;
		this.cover = cover;
		this.pubDate = pubDate;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdbook() {
		return idbook;
	}

	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
