package com.quickchat;

import java.util.Scanner;

/**
 * Console entry point for the QuickChat registration and login feature.
 *
 * PROG5121 - Part 1. Console application only (no GUI / no JOptionPane).
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== QuickChat Registration ===");

        // --- Capture first and last name (used in the login welcome message) ---
        System.out.print("Enter your first name: ");
        login.setFirstName(scanner.nextLine().trim());

        System.out.print("Enter your last name: ");
        login.setLastName(scanner.nextLine().trim());

        // --- Username: loop until correctly formatted ---
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

        // --- Password: loop until it meets the complexity rules ---
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

        // --- Cell phone number: loop until correctly formatted ---
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

        // --- Register the user with the captured details ---
        String registrationResult = login.registerUser(username, password, cellPhoneNumber);
        System.out.println(registrationResult);

        // --- Login ---
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

        scanner.close();
    }
}
