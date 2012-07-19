package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;

public class SmsResponse {
	
	private String to;
	private String messageId;
	private int errorNo;
	private String errorDescription;
	private int wrapperId;
	
	@XmlElement(name="To")
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	@XmlElement(name="MessageID")
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@XmlElement(name="ErrNo")
	public int getErrorNo() {
		return errorNo;
	}
	
	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}
	
	@XmlElement(name="ErrDesc")
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public boolean isSuccess() {
		return this.errorNo == 0;
	}

	@XmlElement(name="WrapperID")
	public int getWrapperId() {
		return wrapperId;
	}

	public void setWrapperId(int wrapperId) {
		this.wrapperId = wrapperId;
	}
}
