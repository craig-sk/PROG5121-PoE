package com.quickchat;

/**
 * Handles user registration and login for the QuickChat application.
 *
 * PROG5121 - Part 1: Registration and login feature.
 *
 * Registration rules:
 *  - Username must contain an underscore and be no more than five characters long.
 *  - Password must be at least eight characters and contain a capital letter,
 *    a number and a special character.
 *  - Cell phone number must contain an international country code and be no more
 *    than ten characters after the code.
 */
public class Login {

    // Stored registration details for the single registered user.
    private String storedUsername;
    private String storedPassword;
    private String storedCellPhoneNumber;
    private String firstName;
    private String lastName;

    // Message constants keep the wording consistent between the console app and tests.
    public static final String USERNAME_FAIL =
            "Username is not correctly formatted; please ensure that your username contains an "
            + "underscore and is no more than five characters in length.";
    public static final String PASSWORD_FAIL =
            "Password is not correctly formatted; please ensure that the password contains at least "
            + "eight characters, a capital letter, a number, and a special character.";
    public static final String CELL_FAIL =
            "Cell number is incorrectly formatted or does not contain an international code; please "
            + "correct the number and try again.";
    public static final String REGISTER_SUCCESS = "Registration successful.";

    /** Default constructor. */
    public Login() {
    }

    /**
     * Convenience constructor used mainly by the unit tests to preset the
     * first and last name that are echoed back on a successful login.
     */
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Checks that the username contains an underscore and is no more than five
     * characters long.
     *
     * @param username the username to validate
     * @return true if correctly formatted, otherwise false
     */
    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    /**
     * Checks that the password is at least eight characters long and contains a
     * capital letter, a number and a special character.
     *
     * @param password the password to validate
     * @return true if the complexity rules are met, otherwise false
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }
        boolean hasEightChars = password.length() >= 8;
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        return hasEightChars && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks that the cell phone number contains an international country code
     * followed by a number that is no more than ten characters long.
     *
     * The regular expression below was adapted from the following source:
     * Ofori-Boateng, D. (2020) 'How to validate phone numbers using regex',
     * Stack Overflow. Available at:
     * https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
     * (Accessed: 14 September 2026).
     *
     * @param cellPhoneNumber the cell phone number to validate
     * @return true if correctly formatted, otherwise false
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null
                && cellPhoneNumber.matches("^\\+\\d{1,3}\\d{9,10}$");
    }

    /**
     * Validates the supplied registration details and, when they are all valid,
     * stores them for a later login.
     *
     * @param username        the desired username
     * @param password        the desired password
     * @param cellPhoneNumber the cell phone number
     * @return a message describing the outcome of the registration
     */
    public String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return USERNAME_FAIL;
        }
        if (!checkPasswordComplexity(password)) {
            return PASSWORD_FAIL;
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return CELL_FAIL;
        }
        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCellPhoneNumber = cellPhoneNumber;
        return REGISTER_SUCCESS;
    }

    /**
     * Verifies that the login details entered match the details stored during
     * registration.
     *
     * @param username the username entered at login
     * @param password the password entered at login
     * @return true if the details match, otherwise false
     */
    public boolean loginUser(String username, String password) {
        return storedUsername != null
                && storedUsername.equals(username)
                && storedPassword != null
                && storedPassword.equals(password);
    }

    /**
     * Returns the messaging for a successful or failed login.
     *
     * @param username the username entered at login
     * @param password the password entered at login
     * @return a welcome message on success, or an error message on failure
     */
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String getStoredUsername() {
        return storedUsername;
    }

    public String getStoredCellPhoneNumber() {
        return storedCellPhoneNumber;
    }
}
