package com.sudhakar.web.smsServer.AOP;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.exceptions.AuthorizationFailedException;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.services.AccountService;
import com.sudhakar.web.smsServer.util.SMSRequest;
import com.sudhakar.web.smsServer.util.SMSResponse;

@Aspect
@Configuration
public class AuthAspect {
	
	// "execution(public * com.sudhakar.web.smsServer.controllers.smsController.*(..))"
	// "execution(public * com.sudhakar.web.smsServer.controllers.smsController.*(..) ) "
	@Autowired
	private AccountService acctService;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(*  com.sudhakar.web.smsServer.controllers.smsController.*(..) )  "  ) 
	public void forControllerWebReq( ) {}
	
	//@Around("execution(* *(..)) && args(httpRequest,httpResponse)")
	/*
	@Before("forControllerWebReq()"  )
	private void checkControllerWebReqAccess(JoinPoint theJoinPt)
	{
		log.info("Invoked AOP Before - " + theJoinPt.getSignature().toString());
	} */
		
	@SuppressWarnings("unchecked")
	@Around("forControllerWebReq()" + "&& args(request,acct)")
	private ResponseEntity<SMSResponse> checkControllerWebReqAccess(ProceedingJoinPoint theJoinPt, SMSRequest request, Account acct)
			throws InvalidNumberException, AuthorizationFailedException {
		log.info("Invoked AOP Around - " + theJoinPt.getSignature().toString());
		HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//HttpServletResponse httpResponse = ResponseContextHolder.
		ResponseEntity<SMSResponse> result = null;
		
		final String username = httpRequest.getHeader("username");
        final String authId = httpRequest.getHeader("authId");
        log.info("UserName: " + username);
        log.info("Auth ID:" + authId);
        
        Optional<Account> account = acctService.getAccount(username, authId);
        if (account.isPresent()) {
        	
        	//httpRequest.setAttribute("userAccount", account.get());
        	acct = account.get();
        	try {
        		log.info("Invoking the controller method " + account.get());
        		result = (ResponseEntity<SMSResponse>)theJoinPt.proceed(new Object[] {request,acct});
        		log.info("checkControllerWebReqAccess - Result: " + result);
        	}
        	catch(InvalidNumberException ex) {
        		log.info("checkControllerWebReqAccess - Invalid number exception ");
        		throw ex;
        	}
        	catch (ServletException|IOException|RuntimeException ex) {
        		log.error("checkControllerWebReqAccess - Unknown exception occurs " + ex.getMessage() );
        		result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        								.body(new SMSResponse("","Unexpected exception occurs"));
        	}
        	catch (Throwable ex) {
        		log.error("checkControllerWebReqAccess - Unknown exception occurs " + ex.getMessage() );
        		result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        								.body(new SMSResponse("","Unexpected exception occurs"));        		 
        	} 

        } else {
        	log.info("checkControllerWebReqAccess - Account not found for username:" + username ); 
        	throw new AuthorizationFailedException("Account not found for username: " + username);
        }        
       
        return result;
	} 
}
