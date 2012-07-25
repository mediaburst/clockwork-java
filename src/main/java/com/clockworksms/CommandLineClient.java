package com.clockworksms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineClient {

	private ClockWorkSmsService service;
	private String apiKey = null;
	private String from = null;
	private List<SMS> messages;
	private Boolean longMessage = null;
	private Boolean truncateMessage = null;
	private boolean ssl = false;
	private InvalidCharacterActionEnum invalidCharacterAction = InvalidCharacterActionEnum.AccountDefault;
	
	public static void main(String[] args) {
		CommandLineClient client = new CommandLineClient();
		client.run();
	}
	
	public CommandLineClient() {
	}
	
	public void run() {
		System.out.println("\nStarting Clockwork SMS Client.\n");
		
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
		
		System.out.println("What is your API Key?");
		apiKey = scanner.next();
		apiKey = apiKey.trim();
		System.out.println("API Key set to " + apiKey + "\n");
		
		System.out.println("Who are the messages from (type 'd' to use account default) ?");
		String input = scanner.next();
		input = input.trim();
		from = "d".equals(input) ? null : input;
		String displayInput = null == from ? "default settings" : from;
		System.out.println("Messages will be sent from " + displayInput + "\n");
		
		System.out.println("By default, allow long message to be sent? Y/N (type 'd' to use account default) ?");
		String yesNo = scanner.next();
		yesNo = yesNo.trim();
		if(!"d".equals(yesNo)) {
			longMessage = ("Y").equals(yesNo.toUpperCase()) ? true : false;
			displayInput = yesNo.toUpperCase();
		}
		else {
			displayInput = "default settings";	
		}
		System.out.println("Send long messages? " + displayInput + "\n");
		
		System.out.println("By default, truncate long messages? Y/N (type 'd' to use account default) ?");
		yesNo = scanner.next();
		yesNo = yesNo.trim();
		if(!"d".equals(yesNo)) {
			truncateMessage = ("Y").equals(yesNo.toUpperCase()) ? true : false;
			displayInput = yesNo.toUpperCase();
		}
		else {
			displayInput = "default settings";	
		}
		System.out.println("Truncate long messages? " + displayInput + "\n");		
		
		System.out.println("Use SSL? Y/N (default is No) ?");
		yesNo = scanner.next();
		yesNo = yesNo.trim();
		if(!"".equals(yesNo)) {
			ssl = ("Y").equals(yesNo.toUpperCase()) ? true : false;
		}
		System.out.println("Use SSL: " + yesNo.toUpperCase() + "\n");		
		
		System.out.println("What is your default invalid character action?");
		System.out.println(" - Use account default - d (default)");
		System.out.println(" - Take no action - 1");
		System.out.println(" - Remove any non-GSM character - 2");
		System.out.println(" - Replace non-GSM characters where possible; remove others - 3");
		System.out.println(" - 1, 2 or 3? (type 'd' to use account default)");
		
		String action = scanner.next();
		action = action.trim();
		
		if(!"d".equals(action)) {
			try {
				int actionCode = Integer.parseInt(action);
				this.invalidCharacterAction = InvalidCharacterActionEnum.getByCode(actionCode);
				System.out.println("Invalid character action set to " + action + "\n");
				
			} catch(Exception e) {
				System.out.println(action + " is not a valid option");
			}
		}
		else {
			System.out.println("Invalid character action set to default\n");
		}
		
		initService();

		System.out.println("Type 'send' to send a message.");
		System.out.println("Type 'credit' to check your credit.");
		System.out.println("Type 'quit' to exit.");
		System.out.println("Type 'help' to list these options.\n");
		
		while(scanner.hasNext()) {
			input = scanner.next();
			input = input.trim();
			
			if("send".equals(input)) {
				messages = new ArrayList<SMS>();
				System.out.println("Type 'add' to add a new sms message.");
				System.out.println("Type 'send' to send all messages.");
				
				while(scanner.hasNext()) {
					input = scanner.next();
					input = input.trim();
					
					if("add".equals(input)) {
						addMessage(scanner);
					}
					else if("send".equals(input)) {
						sendMessages();
						break;
					}
				}
			}
			else if("credit".equals(input)) {
				checkCredit();
			}
			else if("quit".equals(input)) {
					System.out.println("Goodbye");
					System.exit(0);
			}
			else if("help".equals(input)) {
				System.out.println("Type 'send' to send a message.");
				System.out.println("Type 'credit' to check your credit.");
				System.out.println("Type 'quit' to exit.\n");
			}
		}		
	}
	
	private void addMessage(Scanner scanner) {
		System.out.println("To:");
		String to = scanner.next();
		to = to.trim();

		System.out.println("Message:");
		String message = scanner.next();
		message = message.trim();

		SMS sms = new SMS(to, message);

		String explicitValue;
		
		System.out.println("Set explicit from? Y/N");
		String setExplicit = scanner.next();
		setExplicit = setExplicit.trim();
		if("Y".equals(setExplicit.toUpperCase())) {
			System.out.println("Set From:");
			explicitValue = scanner.next();
			explicitValue = explicitValue.trim();
			sms.setFrom(explicitValue);
			System.out.println("From set to: " + explicitValue);
		}
		
		System.out.println("Set unique client ID? Y/N");
		setExplicit = scanner.next();
		setExplicit = setExplicit.trim();
		if("Y".equals(setExplicit.toUpperCase())) {
			System.out.println("Set Client ID:");
			explicitValue = scanner.next();
			explicitValue = explicitValue.trim();
			sms.setClientId(explicitValue);
			System.out.println("Client ID set to: " + explicitValue);
		}
		
		System.out.println("Set explicit allow long message? Y/N");
		setExplicit = scanner.next();
		setExplicit = setExplicit.trim();
		if("Y".equals(setExplicit.toUpperCase())) {
			System.out.println("Allow long message? Y/N");
			String yesNo = scanner.next();
			yesNo = yesNo.trim();
			if(!"".equals(yesNo)) {
				Boolean allowLong = ("Y").equals(yesNo.toUpperCase()) ? true : false;
				sms.setLongMessage(allowLong);
				System.out.println("Allow long message: " + yesNo.toUpperCase() + "\n");
			}
		}

		System.out.println("Set explicit truncate message? Y/N");
		setExplicit = scanner.next();
		setExplicit = setExplicit.trim();
		if("Y".equals(setExplicit.toUpperCase())) {
			System.out.println("Truncate message? Y/N");
			String yesNo = scanner.next();
			yesNo = yesNo.trim();
			if(!"".equals(yesNo)) {
				Boolean allowTrunc = ("Y").equals(yesNo.toUpperCase()) ? true : false;
				sms.setTruncateMessage(allowTrunc);
				System.out.println("Truncate message: " + yesNo.toUpperCase() + "\n");
			}
		}
		
		System.out.println("Set explicit invalid character action? Y/N");
		setExplicit = scanner.next();
		setExplicit = setExplicit.trim();
		if("Y".equals(setExplicit.toUpperCase())) {
			System.out.println(" - Take no action - 1");
			System.out.println(" - Remove any non-GSM character - 2");
			System.out.println(" - Replace non-GSM characters where possible; remove others - 3");
			System.out.println(" - 1, 2 or 3?");
			String action = scanner.next();
			action = action.trim();
			if(!"".equals(action)) {
				try {
					int actionCode = Integer.parseInt(action);
					InvalidCharacterActionEnum customAction = InvalidCharacterActionEnum.getByCode(actionCode);
					sms.setInvalidCharacterAction(customAction);
					
				} catch(Exception e) {
					System.out.println(action + " is not a valid option");
				}
			}
			else {
				System.out.println("Invalid character action set to default\n");
			}
		}
		
		messages.add(sms);
		
		System.out.println("Message added.\n");
	}
	
	private void initService() {
		try {
			service = new ClockWorkSmsService(apiKey);
			service.setFrom(from);
			service.setLongMessage(longMessage);
			service.setTruncateMessage(truncateMessage);
			service.setSsl(ssl);
			service.setInvalidCharacterAction(invalidCharacterAction);
			
		} catch (ClockworkException e) {
			e.printStackTrace();
		}
	}
	
	private void checkCredit() {
		try {			
			long credit = service.checkCredit();
			System.out.println("Your credit is currently " + credit);
		} catch (ClockworkException e) {
			e.printStackTrace();
		}	
	}
	
	private void sendMessages() {
		try {			
			List<ClockworkSmsResult> results = service.send(this.messages);
			
			int count = 0;
			for(ClockworkSmsResult result: results) {
				if(result.isSuccess()){
					System.out.println("Message " + count + " sent with ID: " + result.getId());
				}
				else {
					System.out.println("Error sending message " + count + " : " + result.getErrorMessage());
				}
				count++;
			}
			System.out.println("");
			
		} catch (ClockworkException e) {
			e.printStackTrace();
		}	
	}	
}
