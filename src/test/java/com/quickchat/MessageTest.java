package com.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageTest {

    private static final String TEST1_RECIPIENT = "+27718693002";
    private static final String TEST1_MESSAGE = "Hi Mike, can you join us for dinner tonight?";
    private static final String TEST2_RECIPIENT = "08575975889";
    private static final String TEST2_MESSAGE = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    void setUp() {
        Message.resetForTesting();
    }

    @Test
    void messageLength_withinLimit_returnsReadyToSend() {
        Message message = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertEquals("Message ready to send.", message.validateMessageLength());
    }

    @Test
    void messageLength_exceedsLimit_returnsExceedsMessage() {
        String longMessage = "a".repeat(260);
        Message message = new Message(TEST1_RECIPIENT, longMessage, 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                message.validateMessageLength());
    }

    @Test
    void recipientCell_correctlyFormatted_returnsSuccess() {
        Message message = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    @Test
    void recipientCell_incorrectlyFormatted_returnsFailure() {
        Message message = new Message(TEST2_RECIPIENT, TEST2_MESSAGE, 1);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
                message.checkRecipientCell());
    }

    @Test
    void createMessageHash_returnsExpectedHash() {
        Message message = new Message("0012345678", TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    void checkMessageID_isNoMoreThanTenCharacters() {
        Message message = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    void sentMessage_sendOption_returnsSentConfirmation() {
        Message message = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertEquals("Message successfully sent.", message.sentMessage(1));
    }

    @Test
    void sentMessage_disregardOption_returnsDeletePrompt() {
        Message message = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        assertEquals("Press 0 to delete the message.", message.sentMessage(2));
    }

    @Test
    void sentMessage_storeOption_returnsStoredConfirmation() {
        Message message = new Message(TEST2_RECIPIENT, TEST2_MESSAGE, 1);
        assertEquals("Message successfully stored.", message.sentMessage(3));
    }

    @Test
    void returnTotalMessages_countsSentMessages() {
        Message first = new Message(TEST1_RECIPIENT, TEST1_MESSAGE, 0);
        first.sentMessage(1);
        Message second = new Message(TEST1_RECIPIENT, "Another message", 1);
        second.sentMessage(1);
        assertEquals(2, second.returnTotalMessages());
    }
}
