package com.sudhakar.web.smsServer.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sudhakar.web.smsServer.DAOS.model.Account;

@Service
public interface AccountService {
	public Optional<Account> getAccount(int id);
	
	public Optional<Account> getAccount(String userName, String AuthID);
}
