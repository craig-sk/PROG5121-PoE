package com.quickchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final String STORE_FILE = "stored_messages.json";

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

    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice, please select 1, 2 or 3.";
        }
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }
        StringBuilder builder = new StringBuilder();
        for (Message sent : sentMessages) {
            builder.append("Message ID: ").append(sent.messageID).append(System.lineSeparator())
                    .append("Message Hash: ").append(sent.messageHash).append(System.lineSeparator())
                    .append("Recipient: ").append(sent.recipient).append(System.lineSeparator())
                    .append("Message: ").append(sent.message).append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    public int returnTotalMessages() {
        return sentMessages.size();
    }

    public void storeMessage() {
        storedMessages.add(this);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(STORE_FILE)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static List<Message> readStoredMessages() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(STORE_FILE)) {
            Type listType = new TypeToken<ArrayList<Message>>() {
            }.getType();
            List<Message> messages = gson.fromJson(reader, listType);
            return messages == null ? new ArrayList<>() : messages;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void resetForTesting() {
        sentMessages.clear();
        storedMessages.clear();
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
