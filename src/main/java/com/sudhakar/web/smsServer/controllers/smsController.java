package com.sudhakar.web.smsServer.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sudhakar.web.exceptions.AuthorizationFailedException;
import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.services.AccountService;
import com.sudhakar.web.smsServer.services.impl.SMSServiceImpl;
import com.sudhakar.web.smsServer.util.SMSRequest;
import com.sudhakar.web.smsServer.util.SMSResponse;

@RestController
@RequestMapping("/")
@Validated
public class smsController  {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SMSServiceImpl thisSMSService;
	@Autowired
	private AccountService acctService;
	
	@PostMapping(value="/inbound/sms")
	public ResponseEntity<SMSResponse> handleInboundMessage(@Valid  @RequestBody SMSRequest request
						,@NotNull Account acct) throws InvalidNumberException
	{ 
		log.info("Inbound Message :" + request);
		
		if (acct == null) {
			throw new AuthorizationFailedException("Account not found to process the request");			
		}
		
		thisSMSService.performInboundMessage(request, acct);
		
		SMSResponse response = new SMSResponse("outbound sms ok","");
		log.info("handleOutboundMessage response :" + response);
		
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value="/outbound/sms")
	public ResponseEntity<SMSResponse> handleOutboundMessage(@Valid  @RequestBody SMSRequest request
						,@NotNull Account acct) throws InvalidNumberException
	{ 
		log.info("Outbound Message :" + request);
		
		if (acct == null) {
			throw new AuthorizationFailedException("Account not found to process the request");			
		}
		
		thisSMSService.performOutboundMessage(request, acct);
		
		SMSResponse response = new SMSResponse("outbound sms ok","");
		log.info("handleOutboundMessage response :" + response);
		
		return ResponseEntity.ok().body(response);
	}
	
}
