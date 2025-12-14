package utils;

import java.util.Scanner;

import enums.EquipSlot;

public class Tools {

    private Tools() {
    }

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void waitForUser(Scanner input) {
        System.out.println("\nPress enter to continue...");
        input.nextLine();
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
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static EquipSlot validateEquipSlot(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String userStr = input.nextLine().trim().toUpperCase();
            try {
                return EquipSlot.valueOf(userStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid equip slot.");
            }
        }
    }
}
