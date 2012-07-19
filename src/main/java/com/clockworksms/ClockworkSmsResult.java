package com.clockworksms;


/**
 * Result of sending an SMS to the ClockWork API
 * 
 */
public class ClockworkSmsResult {
	
    private String id;
    private SMS sms;
    private boolean success;
    private int errorCode;
    private String errorMessage;
    
	/**
	 * @param id
	 * @param sms
	 * @param success
	 * @param errorCode
	 * @param errorMessage
	 */
	public ClockworkSmsResult(String id, SMS sms, boolean success, int errorCode, String errorMessage) {
		
		this.id = id;
		this.sms = sms;
		this.success = success;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public SMS getSMS() {
		return sms;
	}
	
	public void setSMS(SMS sms) {
		this.sms = sms;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    

}
