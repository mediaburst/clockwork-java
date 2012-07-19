# Clockwork SMS API Wrapper for Java

This wrapper lets you interact with Clockwork without the hassle of having to create any XML or make HTTP calls.

## What's Clockwork?

The Mediaburst SMS API is being re-branded as Clockwork. At the same time we'll be launching some exciting new features
and these shiny new wrappers.

The terms Clockwork and "Mediaburst SMS API" refer to exactly the same thing.

### Prerequisites

* A [Clockwork][2] account
* Java SE 5 or above

If you're using Java SE 5 there are some additional dependencies for the Java XML bindings, these can be found in lib/. To run the system/unit tests you need the JUnit library, which is also available in lib/.

## Installation

Simply add clockwork-1.0.0.jar to your project. There's a compiled JAR file available in the [downloads][3] section.

## Usage

Add a reference to clockwork-1.0.0.jar and import the uk.com.clockworksms classes

    import uk.com.clockworksms.*;

### Sending a message
    
	ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
	SMS sms = new SMS(TO, MESSAGE);			
	ClockworkSmsResult result = clockWorkSmsService.send(sms);

### Sending multiple messages

We recommend you use batch sizes of 500 messages or fewer. By limiting the batch size it prevents any timeouts when sending.

	List<SMS> messages = new ArrayList<SMS>();
	messages.add( new SMS(TO, MESSAGE) );
	messages.add( new SMS(ANOTHER_TO, MESSAGE) );
			
	ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
	List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);

### Handling the response

The responses come back as `ClockworkSmsResult` objects, these contain the unique Clockwork message ID, whether the message worked (`success`), and the original SMS so you can update your database.

If you send multiple SMS messages in a single send, you'll get back a List of `ClockworkSMSResult` objects, one per SMS.

If a message fails, the reason for failure will be set in `errorCode` and `errorMessage`.  

### Checking your credit

Check how many SMS credits you currently have available. This returns a Long.

    ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);			
    Long credit = clockWorkSmsService.checkCredit();
    
### Handling Errors

The Clockwork wrapper will throw a `ClockworkException` if the entire call failed.

	try {
		ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(INVALID_API_KEY);
		clockWorkSmsService.checkCredit();
	}
    catch (ClockworkException e)
    {
        // Handle the exception
	}
    

### Advanced Usage

This class has a few additional features that some users may find useful, if these are not set your account defaults will be used.

### Optional Parameters

*   from [string]

    The from address displayed on a phone when they receive a message

*   longMessage [boolean]  

    Enable long SMS. A standard text can contain 160 characters, a long SMS supports up to 459.

*   truncateMessage [nullable boolean]  

    Truncate the message payload if it is too long, if this is set to false, the message will fail if it is too long.

*	invalidCharacterAction [InvalidCharacterActionEnum]

	What to do if the message contains an invalid character. Possible values are
    * AccountDefault - Perform your account's default action
	* Error			 - Fail the message
	* Remove		 - Remove the invalid characters then send
	* Replace		 - Replace some common invalid characters such as replacing curved quotes with straight quotes

### Setting Options

#### Global Options

Options set on the API object will apply to all SMS messages unless specifically overridden.

In this example both messages will be sent from Clockwork:

	List<SMS> messages = new ArrayList<SMS>();
			
	SMS sms1 = new SMS();
	messages.add( sms1 );
			
	SMS sms2 = new SMS();
    messages.add( sms1 );
	
	ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
    clockWorkSmsService.setFrom("Clockwork");

	List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
        
#### Per-message Options

Set option values individually on each message.

In this example, one message will be from Clockwork and the other from 84433:

	List<SMS> messages = new ArrayList<SMS>();
			
	SMS sms1 = new SMS();
	sms1.setFrom( "Clockwork" );
	messages.add( sms1 );
			
	SMS sms2 = new SMS();
	sms1.setFrom( "84433" );
	messages.add( sms1 );
			
	ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
	List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);


# License

This project is licensed under the ISC open-source license.

A copy of this license can be found in License.txt.

# Contributing

If you have any feedback on this wrapper drop us an email to hello@clockworksms.com.

The project is hosted on GitHub at https://github.com/mediaburst/clockwork-java.
If you would like to contribute a bug fix or improvement please fork the project 
and submit a pull request.

[1]: https://nuget.org/packages/Clockwork/
[2]: http://www.clockworksms.com/
[3]: https://github.com/mediaburst/clockwork-java/downloads/