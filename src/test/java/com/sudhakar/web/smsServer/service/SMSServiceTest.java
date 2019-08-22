package com.sudhakar.web.smsServer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.smsServer.DAOS.PhoneNumberDAORepository;
import com.sudhakar.web.smsServer.DAOS.model.Account;
import com.sudhakar.web.smsServer.DAOS.model.PhoneNumber;
import com.sudhakar.web.smsServer.services.SMSCacheManager;
import com.sudhakar.web.smsServer.services.SMSService;
import com.sudhakar.web.smsServer.services.impl.SMSServiceImpl;
import com.sudhakar.web.smsServer.util.SMSRequest;

//import junit.framework.Assert;


// Testing SMSService w/o using the cache manager.
public class SMSServiceTest {

	private SMSService thisSMSService;
	
	@Mock
	private SMSCacheManager thisSMSCacheMgr;
	
	@Mock
	private PhoneNumberDAORepository thisPhNumberDAO;
	
	private Account thisAcct;
	
	private String validFromNumber = "";
	private String inValidFromNumber = "";
	private String validToNumber = "";
	private String inValidToNumber = "";
	
	SMSRequest buildSMSRequest(String fromNum, String toNum) {
		return new SMSRequest(fromNum, toNum, "Hello!");
	}
	
	@BeforeEach
	public void setup() {
		thisAcct = new Account();
		MockitoAnnotations.initMocks(this);
		thisSMSService = new SMSServiceImpl();
		thisSMSService.setPhNumDAOReository(thisPhNumberDAO);
		thisSMSService.setCacheManager(thisSMSCacheMgr);		
	}
	
	@Test()		//( expected = Test.None.class /* no exception expected */)
	@Tag("UnitTest")
	public void testProperInboundMessage() throws InvalidNumberException  {
		SMSRequest thisReq = buildSMSRequest(validFromNumber, validToNumber);
		
		//given
		Mockito.when( thisPhNumberDAO.findByNumberAndAccountID(Mockito.any(String.class), Mockito.anyInt() ))
				.thenReturn(new PhoneNumber());
		
		// then
		thisSMSService.performInboundMessage(thisReq, thisAcct);		
	}
	
	@Test
	@Tag("UnitTest")
	public void testProperOutboundMessage() throws InvalidNumberException {

		SMSRequest thisReq = buildSMSRequest(validFromNumber, validToNumber);
		
		//given
		Mockito.when( thisPhNumberDAO.findByNumberAndAccountID(Mockito.any(String.class), Mockito.anyInt() ))
			.thenReturn(new PhoneNumber());
		Mockito.when(thisSMSCacheMgr.isStopped(Mockito.any(String.class), Mockito.any(String.class)))
			.thenReturn(false);
		Mockito.when(thisSMSCacheMgr.doIncrementLimit( Mockito.anyString()))
			.thenReturn(true);

		// then
		thisSMSService.performOutboundMessage(thisReq, thisAcct);
	}
	
	
	@Test
	@Tag("UnitTest")
	public void testInboundReqWithWrongToNumber() {
		SMSRequest thisReq = buildSMSRequest(validFromNumber, inValidToNumber);
		
		//given
		Mockito.when( thisPhNumberDAO.findByNumberAndAccountID(Mockito.any(String.class), Mockito.anyInt() ))
				.thenReturn(null);
		
		// then
		Assertions.assertThrows(InvalidNumberException.class,
				() -> thisSMSService.performInboundMessage(thisReq, thisAcct) );
	}
	
	@Test
	@Tag("UnitTest")
	public void testOutboundReqWithWrongFromNumber() {
		SMSRequest thisReq = buildSMSRequest(inValidFromNumber, validToNumber);
		
		//given
		Mockito.when( thisPhNumberDAO.findByNumberAndAccountID(Mockito.any(String.class), Mockito.anyInt() ))
				.thenReturn(null);
		Mockito.when(thisSMSCacheMgr.isStopped(Mockito.any(String.class), Mockito.any(String.class)))
			.thenReturn(false);
		Mockito.when(thisSMSCacheMgr.doIncrementLimit( Mockito.anyString()))
			.thenReturn(true);

		// then
		Assertions.assertThrows(InvalidNumberException.class, 
				() -> thisSMSService.performOutboundMessage(thisReq, thisAcct) );
	}	
}
