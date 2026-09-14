package com.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Ch&&sec@ke99!";
    private static final String VALID_CELL = "+27838968976";

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login("Kyle", "Smith");
    }

    @Test
    void registerUser_allDetailsValid_returnsSuccess() {
        assertEquals(Login.REGISTER_SUCCESS,
                login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL));
    }

    @Test
    void registerUser_usernameIncorrectlyFormatted_returnsUsernameError() {
        assertEquals(Login.USERNAME_FAIL,
                login.registerUser("kyle!!!!!!!", VALID_PASSWORD, VALID_CELL));
    }

    @Test
    void registerUser_passwordDoesNotMeetComplexity_returnsPasswordError() {
        assertEquals(Login.PASSWORD_FAIL,
                login.registerUser(VALID_USERNAME, "password", VALID_CELL));
    }

    @Test
    void registerUser_cellIncorrectlyFormatted_returnsCellError() {
        assertEquals(Login.CELL_FAIL,
                login.registerUser(VALID_USERNAME, VALID_PASSWORD, "08966553"));
    }

    @Test
    void returnLoginStatus_correctDetails_returnsWelcomeMessage() {
        login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL);
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                login.returnLoginStatus(VALID_USERNAME, VALID_PASSWORD));
    }

    @Test
    void returnLoginStatus_incorrectDetails_returnsErrorMessage() {
        login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL);
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus("wrong_", "WrongPass99!"));
    }

    @Test
    void checkUserName_correctlyFormatted_returnsTrue() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void checkUserName_incorrectlyFormatted_returnsFalse() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void checkPasswordComplexity_meetsRequirements_returnsTrue() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void checkPasswordComplexity_doesNotMeetRequirements_returnsFalse() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneNumber_correctlyFormatted_returnsTrue() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void checkCellPhoneNumber_incorrectlyFormatted_returnsFalse() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    void loginUser_correctDetails_returnsTrue() {
        login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL);
        assertTrue(login.loginUser(VALID_USERNAME, VALID_PASSWORD));
    }

    @Test
    void loginUser_incorrectDetails_returnsFalse() {
        login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL);
        assertFalse(login.loginUser("wrong_", "WrongPass99!"));
    }
}
