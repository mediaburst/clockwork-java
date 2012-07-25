package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Credit")
public class CreditRequest extends XmlRequest {

	private String apiKey;
	
	public CreditRequest() { }
	
	public CreditRequest(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@XmlElement(name="Key")
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Class<CreditResponse> getResponseClassType() {
		return CreditResponse.class;
	}
}
