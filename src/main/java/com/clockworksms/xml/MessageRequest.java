package com.clockworksms.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Message")
@XmlType( propOrder = {"apiKey", "sms"} )

public class MessageRequest extends XmlRequest {
	
	private String apiKey;
	private List<xmlSMS> xmlSMS;

	public MessageRequest() { }

	public MessageRequest(String apiKey) {
		this.apiKey = apiKey;

		this.xmlSMS = new ArrayList<xmlSMS>();
	}
	
	@XmlElement(name="Key")
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
		
	@XmlElement(name="SMS")
	public List<xmlSMS> getSms() {
		return xmlSMS;
	}
	
	public void setSms(List<xmlSMS> xmlSMS) {
		this.xmlSMS = xmlSMS;
	}
	
	public void addMessage(xmlSMS xmlSMS) {
		this.xmlSMS.add(xmlSMS);
	}

	public Class<MessageResponse> getResponseClassType() {
		return MessageResponse.class;
	}
}
