package com.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Login} class.
 *
 * The test data used here matches the data supplied in the PROG5121 Part 1
 * assessment brief so that the methods are marked against the expected values.
 */
class LoginTest {

    // Valid values reused across tests so that only the field under test drives the result.
    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Ch&&sec@ke99!";
    private static final String VALID_CELL = "+27838968976";

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login("Kyle", "Smith");
    }

    // ---------------------------------------------------------------------
    // assertEquals tests (registration messaging)
    // ---------------------------------------------------------------------

    @Test
    void registerUser_allDetailsValid_returnsSuccess() {
        assertEquals(Login.REGISTER_SUCCESS,
                login.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL));
    }

    @Test
    void registerUser_usernameIncorrectlyFormatted_returnsUsernameError() {
        // Test Data: "kyle!!!!!!!" (no underscore and more than five characters)
        assertEquals(Login.USERNAME_FAIL,
                login.registerUser("kyle!!!!!!!", VALID_PASSWORD, VALID_CELL));
    }

    @Test
    void registerUser_passwordDoesNotMeetComplexity_returnsPasswordError() {
        // Test Data: "password"
        assertEquals(Login.PASSWORD_FAIL,
                login.registerUser(VALID_USERNAME, "password", VALID_CELL));
    }

    @Test
    void registerUser_cellIncorrectlyFormatted_returnsCellError() {
        // Test Data: "08966553" (no international code)
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

    // ---------------------------------------------------------------------
    // assertTrue / assertFalse tests (validation methods)
    // ---------------------------------------------------------------------

    @Test
    void checkUserName_correctlyFormatted_returnsTrue() {
        // Test Data: "kyl_1"
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void checkUserName_incorrectlyFormatted_returnsFalse() {
        // Test Data: "kyle!!!!!!!"
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void checkPasswordComplexity_meetsRequirements_returnsTrue() {
        // Test Data: "Ch&&sec@ke99!"
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void checkPasswordComplexity_doesNotMeetRequirements_returnsFalse() {
        // Test Data: "password"
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneNumber_correctlyFormatted_returnsTrue() {
        // Test Data: "+27838968976"
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void checkCellPhoneNumber_incorrectlyFormatted_returnsFalse() {
        // Test Data: "08966553"
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---------------------------------------------------------------------
    // Login verification (assertTrue / assertFalse)
    // ---------------------------------------------------------------------

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
