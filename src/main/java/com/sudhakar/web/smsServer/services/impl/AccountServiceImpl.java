package com.sudhakar.web.smsServer.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudhakar.web.smsServer.DAOS.AccountDAORepository;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AccountDAORepository acctDAO;
	
	@Override
	public Optional<Account> getAccount(int id) {
		log.info(" getAccount i/p :" + id);
		
		Optional<Account> acct =  acctDAO.findById(id);
		
		log.info(" getAccount o/p :" + (acct.isPresent() ? acct.get() : " No Account Found! "));
		return acct; //DAO.findById(id);
	}

	@Override
	public Optional<Account> getAccount(String userName, String AuthID) {
		// TODO Auto-generated method stub
		return Optional.ofNullable( acctDAO.findByUsernameAndAuthId(userName, AuthID));
	}

}
