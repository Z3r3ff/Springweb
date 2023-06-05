package com.btl2.bookstore.book;

public class Rating {
	
	private int idbook;
	private String username;
	private float stars;
	private String review;
	
	
	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Rating(int idbook, String username, float stars, String review) {
		super();
		this.idbook = idbook;
		this.username = username;
		this.stars = stars;
		this.review = review;
	}

	public int getIdbook() {
		return idbook;
	}


	public void setIdbook(int idbook) {
		this.idbook = idbook;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}

