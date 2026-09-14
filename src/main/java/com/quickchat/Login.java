package com.quickchat;

public class Login {

    private String storedUsername;
    private String storedPassword;
    private String storedCellPhoneNumber;
    private String firstName;
    private String lastName;

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

    public Login() {
    }

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

    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

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

    // Regular expression adapted from: Ofori-Boateng, D. (2020) 'Regular expression to match
    // standard 10 digit phone number', Stack Overflow. Available at:
    // https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    // (Accessed: 14 September 2026).
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null
                && cellPhoneNumber.matches("^\\+\\d{1,3}\\d{9,10}$");
    }

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

    public boolean loginUser(String username, String password) {
        return storedUsername != null
                && storedUsername.equals(username)
                && storedPassword != null
                && storedPassword.equals(password);
    }

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
