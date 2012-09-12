package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import com.clockworksms.Balance;

@XmlRootElement(name="Balance_Resp")
public class BalanceResponse extends XmlResponse {
	
  private Float balance;
  private String currencySymbol;
  private String currencyCode;
  
	@XmlElement(name="Balance")
	public Float getBalance() {
		return balance;
	}
	
	public void setBalance(Float balance) {
		this.balance = balance;
	}	
  
  @XmlElementWrapper(name="Currency")
  @XmlElement(name="Symbol")
	public String getCurrencySymbol() {
		return currencySymbol;
	}
	
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}	 
  
  @XmlElementWrapper(name="Currency")
  @XmlElement(name="Code")
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}	 
}
