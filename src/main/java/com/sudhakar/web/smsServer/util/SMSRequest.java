package com.sudhakar.web.smsServer.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import com.sudhakar.web.exceptions.HTTP400Exception;

public class SMSRequest {
	
	public SMSRequest(String fromNum, String toNum, String textMsg) {
		super();
		this.from = fromNum;
		this.to = toNum;
		this.text = textMsg;
	}
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String fromNum) {		
	//	if (!fromNum.matches("\\d+")) throw new HTTP400Exception("From Numbeer is not a valid number");
		this.from = fromNum;
	}
	public String getTo() {		
		return to;
	}
	public void setTo(String toNum) {
	//	if (!to.matches("\\d+")) throw new HTTP400Exception("To Number is not a valid number");
		this.to = toNum;
	}
	public String getText() {
		return text;
	}
	public void setText(String textMsg) {
		this.text = textMsg;
	}

	@Override
	public String toString() {
		return "smsRequest [fromNum=" + from + ", toNum=" + to + ", textMsg=" + text + "]";
	}


	@NotNull(message="Missing mandatory number parameter - From  ")
	@NotBlank(message="From number can't be blank")
	@Size(min=6, max=16, message="Invalid From number, length allowed is 6 to 16 numbers only")
	@Pattern(regexp="\\d+", message="Invalid number format for 'from' parameter")
	String from;

	@NotNull(message="Missing mandatory number parameter - To  ")
	@NotBlank(message="To number can't be blank")
	@Size(min=6, max=16, message="Invalid To number, length allowed is 6 to 16 numbers only")
	@Pattern(regexp="\\d+", message="Invalid number format for 'to' parameter")
	String to;

	@NotNull(message="Missing mandatory String parameter - Text ")
	@NotBlank(message="TextMessage can't be blank")
	String text;
}
