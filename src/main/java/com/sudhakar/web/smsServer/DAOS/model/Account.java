package com.sudhakar.web.smsServer.DAOS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name="account")
public class Account {
	
	public Account() {}
	
	public Account(int id, String authID, String userName) {
		this.id = id;
		this.authID = authID;
		this.userName = userName;
	}

	// Getter, Setters
	public String getAuthID() {
		return authID;
	}

	public void setAuthID(String authID) {
		this.authID = authID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", authID=" + authID + ", userName=" + userName + "]";
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="auth_id", nullable=false)
	private String authID;
	
	@Column(name="username", nullable=false)
	private String userName;
}
