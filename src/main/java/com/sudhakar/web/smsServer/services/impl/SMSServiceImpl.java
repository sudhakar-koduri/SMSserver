package com.sudhakar.web.smsServer.services.impl;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudhakar.web.exceptions.BlockedException;
import com.sudhakar.web.exceptions.HTTP400Exception;
import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.exceptions.LimitExceedException;
//import com.sudhakar.web.smsServer.DAOS.AccountDAORepository;
import com.sudhakar.web.smsServer.DAOS.PhoneNumberDAORepository;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.DAOS.model.PhoneNumber;
import com.sudhakar.web.smsServer.services.SMSCacheManager;
import com.sudhakar.web.smsServer.services.SMSService;
import com.sudhakar.web.smsServer.util.SMSRequest;

@Service
public class SMSServiceImpl implements SMSService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private PhoneNumberDAORepository phNumberDAO;
	
	private SMSCacheManager cacheMgr;
	
	@Autowired
	public void setPhNumDAOReository(PhoneNumberDAORepository phNumDAO) {
		this.phNumberDAO = phNumDAO;		
	}
	
	@Autowired
	public void setCacheManager(SMSCacheManager cacheMgr) {
		this.cacheMgr = cacheMgr;
	}
	
	
	@Override
	public void performInboundMessage( SMSRequest request, Account acct) throws InvalidNumberException {
		
		log.info(">>> performInboundMessage I/P :" + request + " Account : " + acct);
		if (request == null ) throw new HTTP400Exception("Invalid request object to performInboundMessage service");
		
		// Check for the 'to' parameter in the current account 
		PhoneNumber phNum = phNumberDAO.findByNumberAndAccountID(request.getTo(), acct.getId());
		if (phNum == null) {
			log.info("Invalid to number : " + request.getTo() );
			throw new InvalidNumberException("'to' parameter not found in the user account");
		}
		
		// Handle the stop request
		if (request.getText().equalsIgnoreCase("STOP") || 
				request.getText().equalsIgnoreCase("STOP\n") ||
				request.getText().equalsIgnoreCase("STOP\r") ||
				request.getText().equalsIgnoreCase("STOP\r\n") 
				)
		{
			log.info(" Invoked Stop on From :" + request.getFrom() + "; To :" + request.getTo());
			// Add From and To Cache with expiry of 4 hrs.
			cacheMgr.cacheStopDetails(request.getFrom(), request.getTo());
		}
		
		log.info("<<< performInboundMessage");
	}
	
	@Override
	public void performOutboundMessage( SMSRequest request, Account acct) throws InvalidNumberException {
		log.info(">>> performOutboundMessage I/P :" + request + " Account : " + acct);
		if (request == null ) throw new HTTP400Exception("Invalid request object to performInboundMessage service");
		
		// Check for the 'from' parameter in the current account 
		PhoneNumber phNum = phNumberDAO.findByNumberAndAccountID(request.getFrom(), acct.getId());
		if (phNum == null) {
			log.info("Invalid from number : " + request.getTo() );
			throw new InvalidNumberException("'from' parameter not found in the user account");
		}
		
		// Handle the stop request
		if ( cacheMgr.isStopped(request.getFrom(), request.getTo()) ) 
			throw new BlockedException("SMS from " + request.getFrom() + " to " + request.getTo() + " blocked by STOP request");
			
		// Handle the limit for this 'from' number.
		if ( !cacheMgr.doIncrementLimit(request.getFrom()) )
			throw new LimitExceedException("limit reached for from " + request.getFrom());
	}

}
