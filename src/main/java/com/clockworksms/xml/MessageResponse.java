package com.clockworksms.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Message_Resp")
public class MessageResponse extends XmlResponse {

	private List<SmsResponse> smsResponses;
	
	public MessageResponse() {
		this.smsResponses = new ArrayList<SmsResponse>();
	}
	
	@XmlElement(name="SMS_Resp")
	public List<SmsResponse> getSmsResponses() {
		return smsResponses;
	}

	public void setSmsResponses(List<SmsResponse> smsResponses) {
		this.smsResponses = smsResponses;
	}	
}
