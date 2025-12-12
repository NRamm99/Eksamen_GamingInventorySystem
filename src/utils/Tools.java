package utils;

import java.util.Scanner;

public class Tools {

    private Tools() {
    }

    public static void waitForUser(Scanner input) {
        printToConsole("Press enter to continue...", false);
        input.nextLine();
    }

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void printToConsole(String text, boolean clear) {
        if (clear) {
            clearConsole();
        }
        System.out.println(text);
    }

    public static void printToConsole(String text) {
        printToConsole(text, false);
    }

    public static int validateInt(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String userStr = input.nextLine().trim();
            try {
                return Integer.parseInt(userStr);
            } catch (NumberFormatException e) {
                Tools.printToConsole("Invalid input. Please enter a whole number.", false);
                waitForUser(input);
            }
        }
    }

    public static String validateString(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String string = input.nextLine();
            if (string.matches("^[A-Za-zÆØÅæøå\\s]+$")) {
                return string;
            } else {
                Tools.printToConsole("Invalid string. Please enter a valid string.", false);
                waitForUser(input);
            }
        }
    }
}
