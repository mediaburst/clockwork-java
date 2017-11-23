# Clockwork SMS API Wrapper for Java

This wrapper lets you interact with Clockwork without the hassle of having to create any XML or make HTTP calls.

## What's Clockwork?
Clockwork is the SMS API from [Mediaburst][4].  It was previously called the Mediaburst SMS API, but we've re-branded it Clockwork

The terms Clockwork and "mediaburst SMS API" refer to exactly the same thing.


### Prerequisites

* A [Clockwork][2] account
* Java SE 5 or above

If you're using Java SE 5 there are some additional dependencies for the Java XML bindings, these can be found in lib/. To run the system/unit tests you need the JUnit library, which is also available in lib/.

## Installation

Simply add clockwork-*.jar to your project. There's a compiled JAR file available in the [downloads][3] section.

Alternatively you can add it as a dependency to your project via [Maven](http://mvnrepository.com/artifact/com.clockworksms/clockwork):

```
    <dependency>
      <groupId>com.clockworksms</groupId>
      <artifactId>clockwork</artifactId>
      <version>1.x.x</version>
      <scope>compile</scope>
   </dependency>
```

### Compiling

If you want to compile it yourself, here's how:

```
$ git clone git@github.com:mediaburst/clockwork-java.git
$ cd clockwork-java
$ ant dist
```

## Usage

Add a reference to clockwork-*.jar and import the com.clockworksms classes

```java
import com.clockworksms.*;
```

### Sending a message

```java    
ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);  //Be careful not to post your API Keys to public repositories 
SMS sms = new SMS(TO, MESSAGE);			
ClockworkSmsResult result = clockWorkSmsService.send(sms);
```

### Sending multiple messages

We recommend you use batch sizes of 500 messages or fewer. By limiting the batch size it prevents any timeouts when sending.

```java
List<SMS> messages = new ArrayList<SMS>();
messages.add( new SMS(TO, MESSAGE) );
messages.add( new SMS(ANOTHER_TO, MESSAGE) );
		
ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);  //Be careful not to post your API Keys to public repositories 
List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
```

### Handling the response

The responses come back as `ClockworkSmsResult` objects, these contain the unique Clockwork message ID, whether the message worked (`success`), and the original SMS so you can update your database.

If you send multiple SMS messages in a single send, you'll get back a List of `ClockworkSMSResult` objects, one per SMS.

If a message fails, the reason for failure will be set in `errorCode` and `errorMessage`.  

### Checking your credit

Check how many SMS credits you currently have available. This returns a Long.

```java
ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);  //Be careful not to post your API Keys to public repositories 
Long credit = clockWorkSmsService.checkCredit();
```

### Handling Errors

The Clockwork wrapper will throw a `ClockworkException` if the entire call failed.

```java
try {
	ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(INVALID_API_KEY);
	clockWorkSmsService.checkCredit();
} catch (ClockworkException e) {
	// Handle the exception
}
```    

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

```java
List<SMS> messages = new ArrayList<SMS>();
		
SMS sms1 = new SMS();
messages.add( sms1 );
		
SMS sms2 = new SMS();
messages.add( sms1 );

ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
clockWorkSmsService.setFrom("Clockwork");

List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
```

#### Per-message Options

Set option values individually on each message.

In this example, one message will be from Clockwork and the other from 84433:

```java
List<SMS> messages = new ArrayList<SMS>();
		
SMS sms1 = new SMS();
sms1.setFrom( "Clockwork" );
messages.add( sms1 );
		
SMS sms2 = new SMS();
sms1.setFrom( "84433" );
messages.add( sms1 );
		
ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService(API_KEY);
List<ClockworkSmsResult> results = clockWorkSmsService.send(messages);
```

# Testing

We use [JUnit](http://junit.org) as the test framework via [Ant](https://ant.apache.org/).

To run the tests you'll need to add your API key to `src/test/java/com/clockworksms/SmsServiceSystemTest.java` and run `ant test` from the root of the project.

# License

This project is licensed under the ISC open-source license.

A copy of this license can be found in License.txt.

# Contributing

## Feedback

If you have any feedback on this wrapper drop us an email to hello@clockworksms.com.

The project is hosted on GitHub at https://github.com/mediaburst/clockwork-java.
If you would like to contribute a bug fix or improvement please fork the project 
and submit a pull request.

[2]: http://www.clockworksms.com/
[3]: https://github.com/mediaburst/clockwork-java/downloads/
[4]: https://www.mediaburst.co.uk/
