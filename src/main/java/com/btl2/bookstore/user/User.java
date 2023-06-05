package com.btl2.bookstore.user;

import javax.validation.constraints.NotBlank;

public class User {
	private int iduser;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	private int age;
	@NotBlank
	private String address;
	private String role;
	
	public User(int iduser, String username, String password, String name, int age, String address, String role) {
		super();
		this.iduser = iduser;
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
		this.address = address;
		this.role = role;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
