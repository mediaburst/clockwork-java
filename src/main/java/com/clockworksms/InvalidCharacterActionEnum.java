package com.clockworksms;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Action to take if the message text contains an invalid character
 * Valid characters are defined in the GSM 03.38 character set
 */
public enum InvalidCharacterActionEnum {
	
	AccountDefault(0),	// Use default settings from account
	None(1),			// Take no action
	Remove(2),			// Remove any non-GSM character
	Replace(3);			// Replace non-GSM characters where possible; remove others
	
	private static final Map<Integer, InvalidCharacterActionEnum> lookup =
			new HashMap<Integer, InvalidCharacterActionEnum>();
	
	static {
		for(InvalidCharacterActionEnum action: EnumSet.allOf(InvalidCharacterActionEnum.class)) {
			lookup.put(action.getCode(), action);
		}
	}
	
	private int code = 0;
	
	private InvalidCharacterActionEnum(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static InvalidCharacterActionEnum getByCode(int code) {
		if(code < 0 || code > 3) {
			throw new IllegalArgumentException("Code must be between 0 and 3");
		}
		return lookup.get(code);
	}
}
