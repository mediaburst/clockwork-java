package com.clockworksms;

/**
 * Represents errors returned by the ClockWork SMS API
 *
 */
public class ClockworkException extends Exception {
	
	private static final long serialVersionUID = 1949818740698237882L;
	private int code = 0;

	/**
	 * @param message
	 */
	public ClockworkException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 * @param code
	 */
	public ClockworkException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	@Override
	public String toString() {
		return this.getMessage() + " (" + this.code + ")";
	}
	
	/**
	 * @param cause
	 */
	public ClockworkException(Throwable cause) {
		super(cause);
	}
}
