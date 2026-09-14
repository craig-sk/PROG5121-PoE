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
