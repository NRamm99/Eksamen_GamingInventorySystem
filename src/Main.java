import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Player;
import models.items.Item;
import utils.Tools;

public class Main {

    private static Scanner input = new Scanner(System.in);

    private static List<Player> players = new ArrayList<>();
    private static Player currentPlayer = null;;

    public static void main(String[] args) {
        // FileHandler.loadPlayers();

        while (true) {
            if (currentPlayer == null) {
                mainMenu();
            } else {
                promptInventoryMenu();
            }
        }
    }

    private static void promptInventoryMenu() {
        Tools.printToConsole("""
                Inventory Menu
                Please select an option:
                1. View inventory
                2. View equipment
                3. Add item to inventory

                0. Save and back to main menu
                """, true);
        int choice = Tools.validateInt(input, "Please select an option");
        switch (choice) {
            case 1:
                promptViewInventory();
                break;
            case 2:
                printEquipment(currentPlayer);
                break;
            case 3:
                promptAddItemToInventory(currentPlayer);
                break;
            case 0:
                currentPlayer = null;
                break;

            default:
                Tools.printToConsole("Invalid option. Please try again.", false);
                Tools.waitForUser(input);
                promptInventoryMenu();
        }
    }

    private static void promptAddItemToInventory(Player currentPlayer2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'promptAddItemToInventory'");
    }

    private static void printEquipment(Player player) {
        Tools.clearConsole();
        Item[] equipment = player.getEquipment();
        for (int i = 0; i < equipment.length - 1; i++) {
            if (equipment[i] != null) {
                Tools.printToConsole("Slot " + i + ": " + equipment[i].toString(), false);
            } else {
                Tools.printToConsole("Slot " + i + ": Empty", false);
            }
        }
        Tools.printToConsole("Total equipment weight: " + player.getEquipmentWeight() + " kg", false);
        Tools.waitForUser(input);
        promptInventoryMenu();
    }

    private static void promptViewInventory() {
        Tools.clearConsole();
        Item[] inventory = currentPlayer.getInventory();
        for (int i = 0; i < inventory.length - 1; i++) {
            if (inventory[i] != null) {
                Tools.printToConsole("Slot " + i + ": " + inventory[i].toString(), false);
            } else {
                Tools.printToConsole("Slot " + i + ": Empty", false);
            }
        }
        Tools.printToConsole("Total weight: " + currentPlayer.getTotalWeight() + " kg", false); // total weight of inventory and equipment
        Tools.waitForUser(input);
        promptInventoryMenu();
    }

    private static void mainMenu() {
        // FileHandler.savePlayers();
        Tools.printToConsole("""
                Welcome to the game!
                Please select an option:
                1. New player
                2. Load player
                3. Exit
                """, true);

        int choice = Tools.validateInt(input, "Please select an option");

        switch (choice) {
            case 1:
                promptCreatePlayer();
                break;
            case 2:
                promptLoadPlayer();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                Tools.printToConsole("Invalid option. Please try again.", true);
                mainMenu();
        }
    }

    private static void promptLoadPlayer() {
        printPlayers();

        String playerName = Tools.validateString(input, "Please enter the name of the player you want to load");
        for (Player player : players) {
            if (player.getPlayerName().equals(playerName)) {
                currentPlayer = player;
                Tools.printToConsole("Player loaded successfully!", true);
                Tools.waitForUser(input);
                break;
            }
        }
        if (currentPlayer == null) {
            Tools.printToConsole("Player not found. Please try again.", true);
            Tools.waitForUser(input);
            promptLoadPlayer();
        }
    }

    private static void printPlayers() {
        for (Player player : players) {
            Tools.printToConsole(player.getPlayerName(), false);
        }
    }

    private static void promptCreatePlayer() {
        String name = Tools.validateString(input, "Please enter a name for your player");
        Player player = new Player(name);
        players.add(player);
        Tools.printToConsole("Player created successfully!", true);
        Tools.waitForUser(input);

        mainMenu();
    }

}
