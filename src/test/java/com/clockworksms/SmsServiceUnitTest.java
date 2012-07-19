package com.clockworksms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SmsServiceUnitTest {
	
	private static final String API_KEY = "xyz";
	
   @Test
	public void testNullAPIKey() {
	   try {
		   new ClockWorkSmsService(null);
		   fail("Exception should be thrown");
	   } catch (ClockworkException e) {
		   assertEquals("API key cannot be blank", e.getMessage());
	   }
   }
   

   @Test
	public void testBlankAPIKey() {
	   try {
		   new ClockWorkSmsService("");
		   fail("Exception should be thrown");
	   } catch (ClockworkException e) {
		   assertEquals("API key cannot be blank", e.getMessage());
	   }
   }
   
   @Test
	public void testNullSMS() {
	   try {
		   ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
		   SMS sms = null;
		   clockWorkSmsService.send(sms);
		   fail("Exception should be thrown");
	   } catch (ClockworkException e) {
		   assertEquals("SMS cannot be empty", e.getMessage());
	   }
   }
   
   @Test
	public void testEmptyList() {
	   try {
		   ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
		   List<SMS> smsList = new ArrayList<SMS>();
		   clockWorkSmsService.send(smsList);
		   fail("Exception should be thrown");
	   } catch (ClockworkException e) {
		   assertEquals("SMS list can't be empty", e.getMessage());
	   }
   }
   
   @Test
	public void testNullList() {
	   try {
		   ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
		   List<SMS> smsList = null;
		   clockWorkSmsService.send(smsList);
		   fail("Exception should be thrown");
	   } catch (ClockworkException e) {
		   assertEquals("SMS list can't be empty", e.getMessage());
	   }
   }   
}