package com.quickchat;

import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(String recipient, String message, int messageNumber) {
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public Message(String messageID, String recipient, String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Regular expression adapted from: Ofori-Boateng, D. (2020) 'Regular expression to match
    // standard 10 digit phone number', Stack Overflow. Available at:
    // https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    // (Accessed: 18 October 2026).
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{1,3}\\d{9,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international "
                + "code. Please correct the number and try again.";
    }

    public String validateMessageLength() {
        if (message != null && message.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = (message == null ? 0 : message.length()) - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    public final String createMessageHash() {
        if (messageID == null || message == null || message.trim().isEmpty()) {
            return "";
        }
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        String idPart = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }
}
