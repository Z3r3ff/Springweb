package com.btl2.bookstore.book;

import java.util.List;

public class CheckOut {
	private int idcheckout;
	private int iduser;
	private String fullname;
	private String addressorder;
	private String phonenumber;
	private String paymethod = "Cash";
	private float finalprice;
	private List<BookOrder> bookorders = null;
	
	public CheckOut(int idcheckout, int iduser, String fullname,String addressorder, String phonenumber, String paymethod,
			float finalprice) {
		super();
		this.idcheckout = idcheckout;
		this.iduser = iduser;
		this.fullname = fullname;
		this.addressorder = addressorder;
		this.phonenumber = phonenumber;
		this.paymethod = paymethod;
		this.finalprice = finalprice;
	}
	
	public List<BookOrder> getBooks() {
		return bookorders;
	}

	public void setBooks(List<BookOrder> bookorders) {
		this.bookorders = bookorders;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public CheckOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdcheckout() {
		return idcheckout;
	}
	public void setIdcheckout(int idcheckout) {
		this.idcheckout = idcheckout;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getAddressorder() {
		return addressorder;
	}
	public void setAddressorder(String addressorder) {
		this.addressorder = addressorder;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public float getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(float finalprice) {
		this.finalprice = finalprice;
	}
}
