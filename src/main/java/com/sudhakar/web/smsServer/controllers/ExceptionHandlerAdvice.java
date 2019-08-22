package com.sudhakar.web.smsServer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.sudhakar.web.exceptions.HTTP400Exception;
import com.sudhakar.web.exceptions.InvalidNumberException;
import com.sudhakar.web.exceptions.LimitExceedException;
import com.sudhakar.web.exceptions.RestAPIException;
import com.sudhakar.web.exceptions.AuthorizationFailedException;
import com.sudhakar.web.exceptions.BlockedException;
import com.sudhakar.web.smsServer.util.SMSResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

//public abstract class AbstractController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	Counter http400ExceptionCounter  = Metrics.counter("com.rollingstone.ProductController.HTTP400");
	Counter http403ExceptionCounter  = Metrics.counter("com.rollingstone.ProductController.HTTP403");

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<SMSResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException :" + ex.getLocalizedMessage());
        http400ExceptionCounter.increment();
        
        BindingResult result = ex.getBindingResult();
        List<String> validErrors = new ArrayList<String>();
        for (ObjectError objectError : result.getAllErrors()) {
            validErrors.add(objectError.getDefaultMessage());
        }
        SMSResponse response = new SMSResponse("", validErrors.toString()); 
        //smsResponse response = new smsResponse("", "");
        return new ResponseEntity<SMSResponse>(response, HttpStatus.BAD_REQUEST); 
    }
	
	@ExceptionHandler(InvalidNumberException.class)
	public ResponseEntity<SMSResponse> handleNumberNotFoundException(InvalidNumberException ex) {
		log.error("InvalidNumberException :" + ex.getMessage());
        http400ExceptionCounter.increment();
        
        SMSResponse response = new SMSResponse("", ex.getMessage()); 
        return new ResponseEntity<SMSResponse>(response, HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(AuthorizationFailedException.class)
	public ResponseEntity<SMSResponse> handleAuthorizationFailedException(AuthorizationFailedException ex) {
		log.error("AuthorizationFailedException :" + ex.getMessage());
        http403ExceptionCounter.increment();
        
        SMSResponse response = new SMSResponse("", ex.getMessage()); 
        return new ResponseEntity<SMSResponse>(response, HttpStatus.METHOD_NOT_ALLOWED); 
	}
	
	@ExceptionHandler( {BlockedException.class, LimitExceedException.class} )
	public ResponseEntity<SMSResponse> handleSMSException(RestAPIException ex) {
		log.error( ex.getClass().toString() + " : " + ex.getMessage());
		
		SMSResponse response = new SMSResponse("", ex.getMessage());
        return new ResponseEntity<SMSResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<SMSResponse> handleBadRequestException(Exception ex,
			WebRequest request, HttpServletResponse response)
	{
		log.info("Received Bad Request Exception"+ex.getLocalizedMessage());
		http400ExceptionCounter.increment();
		//new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The Request did not have the correct parameters");
		return new ResponseEntity<SMSResponse>(
				new SMSResponse( "", "Unknown exception occurs" )
				, HttpStatus.BAD_REQUEST
				);        
	}
}
