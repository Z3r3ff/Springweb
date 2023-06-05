package com.btl2.bookstore.book;

public class BookOrder {
	private int idbookorder;
	private int idbook;
	private int iduser;
	private int checkout;
	private int quantity;
	private Book book = null;
	public BookOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookOrder(int idbookorder, int idbook, int iduser, int checkout, int quantity) {
		super();
		this.idbookorder = idbookorder;
		this.idbook = idbook;
		this.iduser = iduser;
		this.checkout = checkout;
		this.quantity = quantity;
	}
	public int getIdbookorder() {
		return idbookorder;
	}
	public void setIdbookorder(int idbookorder) {
		this.idbookorder = idbookorder;
	}
	public int getIdbook() {
		return idbook;
	}
	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getCheckout() {
		return checkout;
	}
	public void setCheckout(int checkout) {
		this.checkout = checkout;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
}
