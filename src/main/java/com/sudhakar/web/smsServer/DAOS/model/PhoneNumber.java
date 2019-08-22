package com.sudhakar.web.smsServer.DAOS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table(name="phone_number")
public class PhoneNumber {

	public PhoneNumber() {}
	
	public PhoneNumber(int id, String phoneNumber, Account account) {
		this.id = id;
		this.number = phoneNumber;
		this.account = account;
	}

	// Setter, Getters
	public String getPhoneNumber() {
		return number;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.number = phoneNumber;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PhoneNumber [id=" + id + ", phoneNumber=" + number + ", account=" + account + "]";
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="number", nullable =false)
	private String number;
	
	@ManyToOne
	@JoinColumn(name="account_id", nullable = false)
	private Account account;
}
