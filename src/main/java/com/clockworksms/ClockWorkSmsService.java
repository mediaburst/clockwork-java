package com.clockworksms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.clockworksms.xml.CreditRequest;
import com.clockworksms.xml.CreditResponse;
import com.clockworksms.xml.MessageRequest;
import com.clockworksms.xml.MessageResponse;
import com.clockworksms.xml.SmsResponse;
import com.clockworksms.xml.XmlRequest;
import com.clockworksms.xml.XmlResponse;
import com.clockworksms.xml.xmlSMS;


/**
 * Send text messages through your ClockWork API Account.
 * <p>You will need a ClockWork API Account available from
 * <a href="http://www.clockworksms.com/">http://www.clockworksms.com/</a></p>
 * 
 * <p>This library is distributed under the ISC Open Source license</p>
 * <p>Usage:</p>
 * <pre>
 * {@code
 * try {
 *     ClockWorkSmsService service = new ClockWorkSmsService(API_KEY);
 *     SMS sms = new SMS("441234567890", "Hello World", "Clockwork");
 *     ClockworkSmsResult result = service.send(sms);
 * }
 * catch (ClockworkException e) {
 *     e.printStackTrace();
 * }
 * }
 * </pre>
 */
public class ClockWorkSmsService {

	private static final String SMS_URL = "api.clockworksms.com/xml/send";
	private static final String CREDIT_URL = "api.clockworksms.com/xml/credit";

	private String apiKey;
	private String from;
	private Boolean longMessage = null;
	private Boolean truncateMessage = null;
	private boolean ssl = false;
	private InvalidCharacterActionEnum invalidCharacterAction = null;
	private Proxy proxy;
	private String proxyUsername;
	private String proxyPassword;
	
	private String version = "1.0"; // initial value. Actually read in from Manifest file
	
	/**
	 * Create a new SMS object passing your ClockWork API key
	 * <p>Log in to the Clockwork website to generate a new key for your account</p>
	 * 
	 * @param apiKey
	 * @throws Exception
	 */
	public ClockWorkSmsService(String apiKey) throws ClockworkException {
		
		if(null == apiKey || apiKey.length() == 0) {
			throw new ClockworkException("API key cannot be blank");
		}
		
		this.apiKey = apiKey;
		
		// extract API version form manifest
		URLClassLoader cl = (URLClassLoader) getClass().getClassLoader();
		try {
			URL url = cl.findResource("META-INF/MANIFEST.MF");
			Manifest manifest = new Manifest(url.openStream());
			Attributes attrs = manifest.getMainAttributes();
			this.version = attrs.getValue("Implementation-Version");
		} catch (IOException e) {
		  throw new ClockworkException(e);
		}		
	}

	/**
	 * Log in to the Clockwork website to generate a new key for your account
	 * @param apiKey Clockwork API key
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @param from Number / String to send messages from, this is displayed on the recipients handset
	 * <p>If left blank your account default will be used</p>
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/** 
	 * @param longMessage Allow messages of up to 459 characters
	 * <p>These take up to 3 credits to send</p>
	 * <p>If left blank your account default will be used</p>
	 */
	public void setLongMessage(Boolean longMessage) {
		this.longMessage = longMessage;
	}

	/** 
	 * @param truncateMessage Truncate message if too long
	 * <p>If left blank your account default will be used</p>
	 */
	public void setTruncateMessage(Boolean truncateMessage) {
		this.truncateMessage = truncateMessage;
	}
	
	/**
	 * @param ssl Use SSL when making requests to the API
	 */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
	
	/**
	 * @param invalidCharacterAction
	 * 	What to do if there's an invalid character in your message text
     *  <p>Valid characters are defined in the GSM 03.38 character set</p>
     *  <p>Set this to AccountDefault to use your accounts default setting (default setting)</p>
	 */
	public void setInvalidCharacterAction(
			InvalidCharacterActionEnum invalidCharacterAction) {
		this.invalidCharacterAction = invalidCharacterAction;
	}

	/**
	 * If a proxy servier is used, instantiate the Proxy class and set on this service
	 * @param proxy
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
		
	/**
	 * proxy server username needed if proxy requires Basic authentication
	 * @param proxyUsername
	 */
	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	/**
	 * proxy server password needed if proxy requires Basic authentication
	 * @param proxyPassword
	 */
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	
	/**
	 * Send a single SMS message
	 * 
	 * @param sms SMS to send
	 * @return An SMSResult object, check SMSResult.Success for send status
	 * @throws ClockworkException Thrown when the ClockWork API returns an error - Normally down to an invalid parameter
	 */
	public ClockworkSmsResult send(SMS sms) throws ClockworkException {
		if(null == sms) {
			throw new ClockworkException("SMS cannot be empty");
		}
		// convert single sms to a List
		ArrayList<SMS> smsList = new ArrayList<SMS>(1);
		smsList.add(sms);
		// return first entry in response List
		return send(smsList).get(0);
	}

	/**
	 * Send multiple SMS messages
	 * 
	 * @param smsList List of SMS messages to send
	 * @return List of SMSResult objects, one per message
	 * @throws ClockworkException Thrown when the ClockWork API returns an error - Normally down to an invalid parameter
	 */
	public List<ClockworkSmsResult> send(List<SMS> smsList) throws ClockworkException {
		
		if(null == smsList || smsList.size() == 0) {
			throw new ClockworkException("SMS list can't be empty");
		}

		int wrapperId = 0;
		
		MessageRequest messageRequest = new MessageRequest(this.apiKey);
		
		for(SMS sms: smsList) {
			// if invalidCharacterAction is set at message level use this, else at API/Service level, else use default
			InvalidCharacterActionEnum thisInvalidCharacterAction = 
					sms.getInvalidCharacterAction() != null ? 
							sms.getInvalidCharacterAction() : 
								this.invalidCharacterAction;
					
					thisInvalidCharacterAction = thisInvalidCharacterAction!= null ?
							thisInvalidCharacterAction :
							InvalidCharacterActionEnum.AccountDefault;
 			
			xmlSMS xmlSms = new xmlSMS(
					sms.getTo(),
					sms.getMessage(),
					!"".equals(sms.getFrom()) ? sms.getFrom() : this.from,
					sms.getClientId(),
					sms.getLongMessage() == null ? this.longMessage : sms.getLongMessage(),
					sms.getTruncateMessage() == null ? this.truncateMessage : sms.getTruncateMessage(), 
					sms.getInvalidCharacterAction().getCode(),
					wrapperId++
				);
			
			messageRequest.addMessage(xmlSms);
		}
		
		MessageResponse messageResponse = (MessageResponse) this.postXml(messageRequest);
		
		if(messageResponse.hasError()) {
			throw messageResponse.getException();
		}

		List<ClockworkSmsResult> results = new ArrayList<ClockworkSmsResult>();
		
		for(SmsResponse response: messageResponse.getSmsResponses()) {
			results.add( new ClockworkSmsResult(
					response.getMessageId(),
					smsList.get( response.getWrapperId() ), 
					response.isSuccess(), 
					response.getErrorNo(), 
					response.getErrorDescription())
			);
		}
		
		return results;
	}
		
	/**
	 * Check the number of SMS available to send
	 * 
	 * @return Number of SMS available
	 * @throws ClockworkException
	 */
	public long checkCredit() throws ClockworkException {
		CreditRequest creditRequest = new CreditRequest(this.apiKey);
		CreditResponse creditResponse = (CreditResponse) this.postXml(creditRequest);
		
		if(creditResponse.hasError()) {
			throw creditResponse.getException();
		}
		
		return creditResponse.getCredit();
	}
	
	/**
	 * 
	 * @param xmlRequest
	 * @return
	 * @throws ClockworkException 
	 */
	private XmlResponse postXml(XmlRequest xmlRequest) throws ClockworkException {
		
		String urlAddress = this.getUrlAddress(xmlRequest);
		
		try {
			JAXBContext context = JAXBContext.newInstance( xmlRequest.getClassType() );
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			StringWriter writer = new StringWriter();
			
			marshaller.marshal(xmlRequest, writer);

			String httpResponse = this.makeHttpRequest(urlAddress, writer.toString());
			
			// strip chars before XML prolog - byte order; not needed
			httpResponse = httpResponse.substring( httpResponse.indexOf('<') );

			context = JAXBContext.newInstance( xmlRequest.getResponseClassType() );
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			XmlResponse response = (XmlResponse) unmarshaller.unmarshal(new StringReader(httpResponse));
			
			return response;
			
		} catch (JAXBException e) {
			throw new ClockworkException(e);
		}
	}
	
	private String getUrlAddress(XmlRequest request) {
		// determine if this is a secure connection
		String url = this.ssl ? "https://" : "http://";
		
		// determine service endpoint based on type of class/request passed in
		if( request.getClass() == MessageRequest.class) {
			url += ClockWorkSmsService.SMS_URL;
		}
		else {
			url += ClockWorkSmsService.CREDIT_URL;
		}
		
		return url;
	}
	
	private String makeHttpRequest(String urlAddress, String payLoad) throws ClockworkException {
		try {
			URL url = new URL(urlAddress);
			HttpURLConnection connection;
			
			if(this.proxy != null) {
				if(this.proxyUsername != null && this.proxyPassword != null) {
					Authenticator authenticator = new Authenticator() {

				        public PasswordAuthentication getPasswordAuthentication() {
				            return (new PasswordAuthentication(proxyUsername,
				                    proxyPassword.toCharArray()));
				        }
				    };
				    Authenticator.setDefault(authenticator);
				}
				connection = (HttpURLConnection)url.openConnection(proxy);
			}
			else {
				connection = (HttpURLConnection)url.openConnection();
			}
			
			connection.setDoOutput(true);
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);			
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setFixedLengthStreamingMode(payLoad.getBytes("UTF-8").length);
			connection.setRequestProperty("content-type", "text/xml; charset=utf-8");
			connection.setRequestProperty("User-Agent", "ClockWork Java Wrapper v" + this.version);
			
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(payLoad);
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
			String line;
	        String response = "";
	        
	        while ((line = in.readLine()) != null) {
	            response += line;
	        }
	        
	        out.close();
	        in.close();
	        
			int responseCode = connection.getResponseCode();
			
			if(responseCode != 200) {
				throw new ClockworkException(
						"HTTP Response error posting to Clockwork server " + connection.getResponseMessage());
			}	        
	        connection.disconnect();
	        
	        return response;

		} catch (MalformedURLException e) {
			throw new ClockworkException(e);
		} catch (IOException e) {
			throw new ClockworkException(e);
		}
	}
}
