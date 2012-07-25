package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Credit_Resp")
public class CreditResponse extends XmlResponse {
	
	private long credit;
	@XmlElement(name="Credit")
	public long getCredit() {
		return credit;
	}
	
	public void setCredit(long credit) {
		this.credit = credit;
	}	
}
