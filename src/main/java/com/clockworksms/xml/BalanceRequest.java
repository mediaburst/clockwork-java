package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Balance")
public class BalanceRequest extends XmlRequest {

	private String apiKey;
	
	public BalanceRequest() { }
	
	public BalanceRequest(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@XmlElement(name="Key")
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public Class<BalanceResponse> getResponseClassType() {
		return BalanceResponse.class;
	}
}
