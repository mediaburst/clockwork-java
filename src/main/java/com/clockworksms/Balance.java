package com.clockworksms;

/**
 * A balance object
 *
 */
public class Balance {
	
	private Float balance = null;
	private String currencySymbol = null;
	private String currencyCode = null;
  
	/**
	 * Create a single text message
	 */
	public Balance() { }
	
	public Balance(Float balance, String currencySymbol, String currencyCode) {
		this.balance = balance;
		this.currencySymbol = currencySymbol;
    this.currencyCode = currencyCode;
	}
  
  public Float getBalance() {
    return this.balance;
  }
  
  public String getCurrencySymbol() {
    return this.currencySymbol;
  }
  
  public String getCurrencyCode() {
    return this.currencyCode;
  }

}