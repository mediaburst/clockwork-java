package com.clockworksms;

/**
 * A single text message
 *
 */
public class SMS {
	
	private String to = null;
	private String message = null;
	private String from = null;
	private String clientId = null;
	private Boolean longMessage = null;
	private Boolean truncateMessage = null;
	private InvalidCharacterActionEnum invalidCharacterAction = InvalidCharacterActionEnum.AccountDefault;
	
	/**
	 * Create a single text message
	 */
	public SMS() { }
	
	public SMS(String to, String message) {
		this.to = to;
		this.message = message;
	}
	
	public SMS(String to, String message, String from) {
		this(to, message);
		this.from = from;
	}
	
	public SMS(String to, String message, String from, String clientId) {
		this(to, message, from);
		this.clientId = clientId;
	}
	
	public SMS(String to, String message, String from, String clientId, Boolean longMessage) {
		this(to, message, from, clientId);
		this.longMessage = longMessage;
	}
	
	public SMS(String to, String message, String from, String clientId, Boolean longMessage, Boolean truncateMessage) {
		this(to, message, from, clientId, longMessage);
		this.truncateMessage = truncateMessage;
	}

	public String getTo() {
		return to;
	}

	/**
	 * Phone number the message is for
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Message text
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	/**
	 * From address displayed on the user's phone
	 * If left blank your account default will be used
	 * @param from 
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	public String getClientId() {
		return clientId;
	}

	/**
	 * Your identifier for this message. (optional)
	 * For example this could be your database record ID
	 * @param clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Boolean getLongMessage() {
		return longMessage;
	}

	/**
	 * Send message longer than 160 characters (optional)
	 * If left blank your account default will be used
	 * @param longMessage
	 */
	public void setLongMessage(Boolean longMessage) {
		this.longMessage = longMessage;
	}

	public Boolean getTruncateMessage() {
		return truncateMessage;
	}

	/**
	 * Trim the message text if it's too long (optional)
	 * If left blank your account default will be used
	 * @param truncateMessage
	 */
	public void setTruncateMessage(Boolean truncateMessage) {
		this.truncateMessage = truncateMessage;
	}

	public InvalidCharacterActionEnum getInvalidCharacterAction() {
		return invalidCharacterAction;
	}

	/**
	 * What to do if there's an invalid character in your message text
     * Valid characters are defined in the GSM 03.38 character set
     * Default is set to AccountDefault which uses your accounts default setting
	 * @param invalidCharacterAction
	 */
	public void setInvalidCharacterAction(
			InvalidCharacterActionEnum invalidCharacterAction) {
		this.invalidCharacterAction = invalidCharacterAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime
				* result
				+ ((invalidCharacterAction == null) ? 0
						: invalidCharacterAction.hashCode());
		result = prime * result
				+ ((longMessage == null) ? 0 : longMessage.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result
				+ ((truncateMessage == null) ? 0 : truncateMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SMS other = (SMS) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (invalidCharacterAction != other.invalidCharacterAction)
			return false;
		if (longMessage == null) {
			if (other.longMessage != null)
				return false;
		} else if (!longMessage.equals(other.longMessage))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (truncateMessage == null) {
			if (other.truncateMessage != null)
				return false;
		} else if (!truncateMessage.equals(other.truncateMessage))
			return false;
		return true;
	}	
}
