package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;

public class xmlSMS {
	
	private String to;
	private String from;
	private String content;
	private String clientId;
	private int wrapperId;
	private Boolean longMessage;
	private Boolean truncateMessage;
	private int invalidCharacterAction;

	public xmlSMS() { }
	
	public xmlSMS(
			String to, String content, String from, 
			String clientId, Boolean longMessage, 
			Boolean truncateMessage, int invalidCharacterAction, int wrapperId) {
		this.to = to;
		this.content = content;
		this.from = from;		
		this.clientId = clientId;
		this.longMessage = longMessage;
		this.truncateMessage = truncateMessage;
		this.invalidCharacterAction = invalidCharacterAction;
		this.wrapperId = wrapperId;
	}
	
	@XmlElement(name="To")
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	@XmlElement(name="From")
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	@XmlElement(name="Content")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@XmlElement(name="ClientID")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@XmlElement(name="WrapperID")
	public int getWrapperId() {
		return wrapperId;
	}

	public void setWrapperId(int wrapperId) {
		this.wrapperId = wrapperId;
	}

	@XmlElement(name="Long")
	public Boolean getLongMessage() {
		return longMessage;
	}

	public void setLongMessage(Boolean longMessage) {
		this.longMessage = longMessage;
	}

	@XmlElement(name="Truncate")
	public Boolean getTruncateMessage() {
		return truncateMessage;
	}

	public void setTruncateMessage(Boolean truncateMessage) {
		this.truncateMessage = truncateMessage;
	}

	@XmlElement(name="InvalidCharAction")
	public int getInvalidCharacterAction() {
		return invalidCharacterAction;
	}

	public void setInvalidCharacterAction(int invalidCharacterAction) {
		this.invalidCharacterAction = invalidCharacterAction;
	}
}
