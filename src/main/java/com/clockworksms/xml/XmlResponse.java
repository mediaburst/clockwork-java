package com.clockworksms.xml;

import javax.xml.bind.annotation.XmlElement;

import com.clockworksms.ClockworkException;


public abstract class XmlResponse {

	private int errorNumber;
	private String errorDescription;

	public boolean hasError() {
		return this.errorNumber > 0;
	}
	
	public ClockworkException getException() {
		return new ClockworkException(this.errorDescription, this.errorNumber);
	}

	@XmlElement(name = "ErrNo")
	public int getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	@XmlElement(name = "ErrDesc")
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
