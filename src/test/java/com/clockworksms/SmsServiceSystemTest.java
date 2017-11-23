package com.clockworksms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SmsServiceSystemTest {

	private static final String API_KEY = "";
	private static final String FROM = "Clockwork";
	private static final String CLIENT_ID = "Test Client ID";
	private static final String TO = "441234567890";
	private static final String ANOTHER_TO = "440987654321";
	private static final String MESSAGE = "Hello World";
	// more than 160 chars
	private static final String LONG_MESSAGE = 
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" +
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String INVALID_MESSAGE = "Hello~World";
	private static final String INVALID_API_KEY = "zzz";
	private static final String BAD_TO = "XX1234567890";

	@Test
	public void testSingleSMS() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			
			SMS sms = new SMS(TO, MESSAGE, FROM, CLIENT_ID);			
			ClockworkSmsResult result = clockWorkSmsService.send(sms);

			assertNotNull("result should not be null", result);
			assertTrue("result should be a success", result.isSuccess());
			assertNotNull("result ID should not be null", result.getId());
			assertEquals("sms attached to result is not the same as the one sent", sms, result.getSMS());
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}	
	}

	@Test
	public void testMultiSMS() {
		try {
			List<SMS> messages = new ArrayList<SMS>();
			messages.add( new SMS(TO, MESSAGE) );
			messages.add( new SMS(ANOTHER_TO, MESSAGE) );
			
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
			
			assertEquals("result size should be 2", 2, results.size());
			
			for (ClockworkSmsResult result: results) {
				if(!result.isSuccess()){
					fail("sms results should return success");
				}
				
				assertNotNull("result ID should not be null", result.getId());
			}			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}	
	}

	@Test
	public void testMultiSMSBothNoMessage() {
		try {
			List<SMS> messages = new ArrayList<SMS>();
			
			SMS sms1 = new SMS();
			sms1.setTo(TO);
			messages.add( sms1 );
			
			SMS sms2 = new SMS();
			sms2.setTo(ANOTHER_TO);
			messages.add( sms1 );
			
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
			
			assertEquals("result size should be 2", 2, results.size());
			
			for (ClockworkSmsResult result: results) {
				if(result.isSuccess()){
					fail("sms results should not return success");
				}
			}			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}	
	}
	
	@Test
	public void testMultiSMSOneNoMessage() {
		try {
			List<SMS> messages = new ArrayList<SMS>();
			
			SMS sms1 = new SMS();
			sms1.setTo(TO);
			sms1.setMessage(MESSAGE);
			messages.add( sms1 );
			
			SMS sms2 = new SMS();
			sms2.setTo(ANOTHER_TO);
			messages.add( sms2 );
			
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
			
			assertEquals("result size should be 2", 2, results.size());
			
			ClockworkSmsResult result1 = results.get(0);
			assertTrue("first message should be a success", result1.isSuccess());
			
			ClockworkSmsResult result2 = results.get(1);
			assertFalse("second message should not be a success", result2.isSuccess());
			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}	
	}

	@Test
	public void testSingleSMSLongMessage() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			clockWorkSmsService.setLongMessage(false);
			
			SMS sms = new SMS(TO, LONG_MESSAGE);
			
			ClockworkSmsResult result = clockWorkSmsService.send(sms);
			assertFalse("result should not be a success", result.isSuccess());
			assertEquals("error code is incorrect", 12, result.getErrorCode()); // Message too long
			
			clockWorkSmsService.setLongMessage(true);
			sms.setLongMessage(false);
			result = clockWorkSmsService.send(sms);
			assertFalse("result should not be a success", result.isSuccess());
			assertEquals("error code is incorrect", 12, result.getErrorCode()); // Message too long
			
			clockWorkSmsService.setLongMessage(false);
			sms.setLongMessage(true);
			result = clockWorkSmsService.send(sms);
			assertTrue("result should be a success", result.isSuccess());			
			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testSingleSMSTruncateMessage() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			clockWorkSmsService.setLongMessage(true);
			clockWorkSmsService.setTruncateMessage(true);
			
			SMS sms = new SMS(TO, LONG_MESSAGE);
			
			ClockworkSmsResult result = clockWorkSmsService.send(sms);
			assertTrue("result should be a success", result.isSuccess());
			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testSingleSMSInvalidCharacter() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			clockWorkSmsService.setInvalidCharacterAction(InvalidCharacterActionEnum.Remove);
			
			SMS sms = new SMS(TO, INVALID_MESSAGE);
			
			ClockworkSmsResult result = clockWorkSmsService.send(sms);
			assertTrue("result should be a success", result.isSuccess());
			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}
	}	
	
	@Test
	public void testSingleSMSInvalidNumber() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
			
			SMS sms = new SMS(BAD_TO, MESSAGE);			
			ClockworkSmsResult result = clockWorkSmsService.send(sms);

			assertNotNull("result should not be null", result);
			assertFalse("result not should be a success", result.isSuccess());
			assertEquals("error code should be 10", 10, result.getErrorCode());
			
		} catch (ClockworkException e) {
			fail("should not throw Exception: " + e.getMessage());
		}	
	}

	@Test
	public void testCredit() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);			
			Long credit = clockWorkSmsService.checkCredit();
			assertNotNull("credit should not be null", credit);
			assertTrue("credit should be greater than zero", credit > 0);
		} catch (ClockworkException e) {
			fail("should not throw Exception");
		}	
	}
  
	@Test
	public void testBalance() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);			
			Balance balance = clockWorkSmsService.checkBalance();
			assertNotNull("balance should not be null", balance.getBalance());
			assertTrue("balance should be greater than zero", balance.getBalance() > 0);
			assertNotNull("currency symbol should be set", balance.getCurrencySymbol());
			assertNotNull("currency code should be set", balance.getCurrencyCode());
		} catch (ClockworkException e) {
			fail("should not throw Exception");
		}	
	}

	@Test
	public void testInvalidApiKey() {
		try {
			ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(INVALID_API_KEY);
			clockWorkSmsService.checkCredit();
			fail("should throw Exception");
		} catch (ClockworkException e) {
			// do nothing
		}
	}	
}
