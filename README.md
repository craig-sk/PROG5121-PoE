# QuickChat - PROG5121 Portfolio of Evidence

Java (Maven) console application for the PROG5121 Programming 1A PoE.

## Part 1 - Registration and Login

A console-based registration and login feature.

**Registration rules**

| Field | Rule |
| --- | --- |
| Username | Contains an underscore and is no more than five characters long. |
| Password | At least eight characters, with a capital letter, a number and a special character. |
| Cell phone number | Contains an international country code followed by a number of no more than ten characters. |

### The `Login` class

| Method | Returns | Purpose |
| --- | --- | --- |
| `checkUserName(String)` | `boolean` | Validates the username format. |
| `checkPasswordComplexity(String)` | `boolean` | Validates the password complexity. |
| `checkCellPhoneNumber(String)` | `boolean` | Validates the cell phone number (regex). |
| `registerUser(String, String, String)` | `String` | Validates and stores the details, returning a status message. |
| `loginUser(String, String)` | `boolean` | Verifies the login details against the stored details. |
| `returnLoginStatus(String, String)` | `String` | Returns the welcome or error message for a login attempt. |

## Part 2 - Sending Messages

After a successful login the app displays "Welcome to QuickChat." and a numeric menu:

1. Send Messages
2. Show recently sent messages (returns "Coming Soon.")
3. Quit

The user chooses how many messages to send. For each message the app validates the recipient
cell number and the message length (max 250 characters), generates a random ten-digit Message ID
and a Message Hash, and lets the user send, disregard, or store the message. Stored messages are
written to a JSON file (`stored_messages.json`) using Gson. The total number of messages sent is
shown when the user quits.

### The `Message` class

| Method | Returns | Purpose |
| --- | --- | --- |
| `checkMessageID()` | `boolean` | Confirms the message ID is no more than ten characters. |
| `checkRecipientCell()` | `String` | Validates the recipient cell number (international code). |
| `validateMessageLength()` | `String` | Confirms the message is 250 characters or fewer. |
| `createMessageHash()` | `String` | Builds the message hash, e.g. `00:0:HITONIGHT`. |
| `sentMessage(int)` | `String` | Sends, disregards, or stores the message. |
| `printMessages()` | `String` | Returns all messages sent while running. |
| `returnTotalMessages()` | `int` | Returns the total number of messages sent. |
| `storeMessage()` | `void` | Stores messages to a JSON file. |

Tests for this class are in `src/test/java/com/quickchat/MessageTest.java`.

## Running

Open the project in NetBeans (or any IDE with Maven support) and run `com.quickchat.Main`,
or from the command line:

```bash
mvn compile
mvn exec:java -Dexec.mainClass=com.quickchat.Main
```

## Testing

Unit tests live in `src/test/java/com/quickchat/LoginTest.java` and use the test data from the
assessment brief.

```bash
mvn test
```

Tests are run automatically on every push via GitHub Actions (`.github/workflows/maven.yml`).
