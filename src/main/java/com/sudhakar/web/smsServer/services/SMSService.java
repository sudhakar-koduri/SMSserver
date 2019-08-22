package com.sudhakar.web.smsServer.services;

import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.smsServer.DAOS.PhoneNumberDAORepository;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.util.SMSRequest;

public interface SMSService {

	void setPhNumDAOReository(PhoneNumberDAORepository phNumberDAO);
	public void setCacheManager(SMSCacheManager cacheMgr);
	
	void performInboundMessage( SMSRequest request, Account acct) throws InvalidNumberException;
	
	void performOutboundMessage( SMSRequest request, Account acct) throws InvalidNumberException;
}
