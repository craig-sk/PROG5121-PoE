package com.quickchat;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== QuickChat Registration ===");

        System.out.print("Enter your first name: ");
        login.setFirstName(scanner.nextLine().trim());

        System.out.print("Enter your last name: ");
        login.setLastName(scanner.nextLine().trim());

        String username;
        while (true) {
            System.out.print("Enter a username (must contain an underscore and be no more than five characters): ");
            username = scanner.nextLine().trim();
            if (login.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            }
            System.out.println(Login.USERNAME_FAIL);
        }

        String password;
        while (true) {
            System.out.print("Enter a password (min 8 chars, a capital, a number and a special character): ");
            password = scanner.nextLine().trim();
            if (login.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            }
            System.out.println(Login.PASSWORD_FAIL);
        }

        String cellPhoneNumber;
        while (true) {
            System.out.print("Enter your cell phone number (e.g. +27838968976): ");
            cellPhoneNumber = scanner.nextLine().trim();
            if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                System.out.println("Cell number successfully captured.");
                break;
            }
            System.out.println(Login.CELL_FAIL);
        }

        System.out.println(login.registerUser(username, password, cellPhoneNumber));

        System.out.println();
        System.out.println("=== QuickChat Login ===");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine().trim();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine().trim();

            System.out.println(login.returnLoginStatus(loginUsername, loginPassword));
            loggedIn = login.loginUser(loginUsername, loginPassword);
        }

        runQuickChat(scanner);

        scanner.close();
    }

    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        Message messageService = null;
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Please choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    messageService = sendMessages(scanner);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose 1, 2 or 3.");
            }
        }

        int total = messageService == null ? 0 : messageService.returnTotalMessages();
        System.out.println();
        System.out.println("Total number of messages sent: " + total);
        System.out.println("Goodbye.");
    }

    private static Message sendMessages(Scanner scanner) {
        int numMessages;
        while (true) {
            System.out.print("How many messages do you wish to send? ");
            try {
                numMessages = Integer.parseInt(scanner.nextLine().trim());
                if (numMessages > 0) {
                    break;
                }
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        Message lastMessage = null;
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("--- Message " + (i + 1) + " of " + numMessages + " ---");

            String recipient;
            while (true) {
                System.out.print("Enter the recipient cell number (e.g. +27718693002): ");
                recipient = scanner.nextLine().trim();
                String cellResult = new Message(recipient, "check", i).checkRecipientCell();
                System.out.println(cellResult);
                if (cellResult.equals("Cell phone number successfully captured.")) {
                    break;
                }
            }

            String messageText;
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = scanner.nextLine();
                String lengthResult = new Message(recipient, messageText, i).validateMessageLength();
                System.out.println(lengthResult);
                if (lengthResult.equals("Message ready to send.")) {
                    break;
                }
            }

            Message message = new Message(recipient, messageText, i);
            System.out.println("Message ID generated: " + message.getMessageID());

            int action;
            while (true) {
                System.out.println("Choose an action for this message:");
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message to send later");
                System.out.print("Enter your choice: ");
                try {
                    action = Integer.parseInt(scanner.nextLine().trim());
                    if (action >= 1 && action <= 3) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    // fall through to the prompt below
                }
                System.out.println("Please choose 1, 2 or 3.");
            }

            System.out.println(message.sentMessage(action));

            if (action == 1) {
                System.out.println();
                System.out.println("Message details:");
                System.out.println("Message ID: " + message.getMessageID());
                System.out.println("Message Hash: " + message.getMessageHash());
                System.out.println("Recipient: " + message.getRecipient());
                System.out.println("Message: " + message.getMessage());
            }

            lastMessage = message;
        }

        return lastMessage;
    }
}
