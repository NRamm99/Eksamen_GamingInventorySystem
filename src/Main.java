import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.EquipSlot;
import exceptions.*;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import utils.FileHandler;
import utils.ItemInitializer;
import utils.Tools;

public class Main {

    // Master item list
    private static List<Item> itemsList = new ArrayList<>();
    // Inventory system
    private static InventorySystem inventorySystem = new InventorySystem(); // Initializing the inventory will also load
                                                                            // the inventory from file
    // Scanner for user input
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize items list
        itemsList = ItemInitializer.initializeItemsList();

        // Main loop
        while (true) {
            // Prompt the main menu
            promptMainMenu();
        }
    }

    // Save inventory to file
    private static void saveInventory() {
        // Exception handling
        try {
            // Use FileHandler to save the inventory to file
            FileHandler.save(inventorySystem.getInventory());
        } catch (IOException e) {
            // Catch IOException and print error message
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void promptMainMenu() {
        Tools.printToConsole("""
                =============== MAIN MENU ===============
                [1] View Inventory
                [2] View Equipment
                [3] Add Item
                [4] Remove Item
                [5] Equip Item
                [6] Unequip Item
                [7] Manage Inventory
                [8] Expand Inventory
                [9] Find items
                [10] Reset inventory & equipment

                [0] Exit
                ===============================================
                """, true);
        int choice = Tools.validateInt(input, "Enter your choice");
        switch (choice) {
            case 1:
                promptViewInventory();
                break;
            case 2:
                promptViewEquipment();
                break;
            case 3:
                promptAddItem();
                break;
            case 4:
                promptRemoveItem();
                break;
            case 5:
                promptEquipItem();
                break;
            case 6:
                promptUnequipItem();
                break;
            case 7:
                promptManageInventory();
                break;
            case 8:
                promptExpandInventory();
                break;
            case 9:
                promptFindItemById();
                break;
            case 10:
                resetInventory();
                break;
            case 0:
                // Save inventory to file
                saveInventory();
                Tools.printToConsole("Exiting program...", false);
                System.exit(0);
                break;
            default:
                // If the choice is invalid, print error message and wait for user input
                Tools.printToConsole("Invalid choice. Please try again.", false);
                Tools.waitForUser(input);
                break;
        }
    }

    private static void resetInventory() {
        // Reset the inventory
        inventorySystem.resetInventory();
        // Print success message and wait for user input
        Tools.printToConsole("Inventory and equipment reset successfully.", false);
        Tools.waitForUser(input);
    }

    private static void promptViewInventory() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);
        Tools.waitForUser(input);

    }

    private static void promptViewEquipment() {
        Tools.printToConsole(inventorySystem.getEquipmentStats(), true);
        Tools.waitForUser(input);
    }

    private static void promptAddItem() {

        Tools.printToConsole("""
                =============== ADD ITEM ===============
                [1] Add Consumable
                [2] Add Weapon
                [3] Add Armor
                [0] Exit
                ===============================================
                """, true);
        int choice = Tools.validateInt(input, "Enter your choice");
        switch (choice) {
            case 1:
                promptAddConsumable();
                break;
            case 2:
                promptAddWeapon();
                break;
            case 3:
                promptAddArmor();
                break;
            case 0:
                break;
            default:
                Tools.printToConsole("Invalid choice. Please try again.", false);
                Tools.waitForUser(input);
                break;
        }

    }

    private static void promptAddArmor() {
        // Print the list of armors
        printArmorsList();
        // Get user input for the ID of the armor to add
        int id = Tools.validateInt(input, "Enter the ID of the armor you want to add:");
        Item item = null;
        // Get item by ID from the master item list
        try {
            item = getItemById(id); // Use the getItemById method to get the item by ID
        } catch (ItemNotFoundException e) {
            // If the item is not found, print error message and wait for user input
            Tools.printToConsole("Item not found: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Check if item is an armor
        if (!(item instanceof Armor)) {
            // If the item is not an armor, print error message and wait for user input
            Tools.printToConsole("Item is not a armor.", false);
            Tools.waitForUser(input);
            return; // Exit the method if the item is not an armor
        }
        // Validate quantity
        int quantity = Tools.validateInt(input, "Enter the quantity of the armor you want to add:");
        // Add armor to inventory using the add method from the inventory system
        for (int i = 0; i < quantity; i++) {
            try {
                inventorySystem.add(item);
            } catch (StackLimitReachedException | QuantityTooLowException | WeightLimitReachedException
                    | InventoryFullException e) {
                // If there is an error adding the item, print error message and wait for user input
                Tools.printToConsole("Error adding item: " + e.getMessage(), false);
                Tools.waitForUser(input);
            }
        }
        // Print success message and wait for user input
        Tools.printToConsole("Armor added successfully.", false);
        Tools.waitForUser(input);
    }

    private static void promptAddWeapon() {
        // Print the list of weapons
        printWeaponsList();
        // Get user input for the ID of the weapon to add
        int id = Tools.validateInt(input, "Enter the ID of the weapon you want to add:");
        Item item = null;
        // Get item by ID from the master item list
        try {
            item = getItemById(id); // Use the getItemById method to get the item by ID
        } catch (ItemNotFoundException e) {
            // If the item is not found, print error message and wait for user input
            Tools.printToConsole("Item not found: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Check if item is a weapon
        if (!(item instanceof Weapon weapon)) {
            // If the item is not a weapon, print error message and wait for user input
            Tools.printToConsole("Item is not a weapon.", false);
            Tools.waitForUser(input);
            return; // Exit the method if the item is not a weapon
        }
        // Validate quantity
        int quantity = Tools.validateInt(input,
                "Enter the quantity of the weapon you want to add");
        // Add weapon to inventory using the add method from the inventory system
        for (int i = 0; i < quantity; i++) {
            try {
                inventorySystem.add(weapon); // Use the add method from the inventory system to add the weapon
            } catch (StackLimitReachedException | QuantityTooLowException | WeightLimitReachedException
                    | InventoryFullException e) {
                Tools.printToConsole("Error adding item: " + e.getMessage(), false);
                Tools.waitForUser(input);
            }
        }
        // Print success message and wait for user input
        Tools.printToConsole("Weapon added successfully.", false);
        Tools.waitForUser(input);
    }

    private static void promptAddConsumable() {
        printConsumablesList();
        int id = Tools.validateInt(input, "Enter the ID of the consumable you want to add:");
        Item item = null;
        // Get item by ID
        try {
            item = getItemById(id);
        } catch (ItemNotFoundException e) {
            Tools.printToConsole("Item not found: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Check if item is a consumable
        if (!(item instanceof Consumable consumable)) {
            Tools.printToConsole("Item is not a consumable.", false);
            Tools.waitForUser(input);
            return;
        }
        // Validate quantity
        int quantity = Tools.validateInt(input,
                "Enter the quantity of the consumable you want to add: (max: " + consumable.getMaxStack() + ")");
        // Set quantity
        try {
            consumable.setQuantity(quantity);
        } catch (InvalidQuantityException e) {
            Tools.printToConsole("Invalid quantity. Please try again.", false);
            Tools.waitForUser(input);
            return;
        }
        // Add consumable to inventory
        try {
            inventorySystem.add(consumable);
        } catch (StackLimitReachedException | QuantityTooLowException | WeightLimitReachedException
                | InventoryFullException e) {
            Tools.printToConsole("Error adding item: " + e.getMessage(), false);
            Tools.waitForUser(input);
            return;
        }
        Tools.printToConsole("Consumable added successfully.", false);
        Tools.waitForUser(input);

    }

    private static void promptRemoveItem() {
        // Print the inventory
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);

        // Get user input for the ID of the item to remove
        int id = Tools.validateInt(input, "Enter the ID of the item you want to remove:");
        try {
            inventorySystem.remove(id); // Use the remove method from the inventory system to remove the item
            Tools.printToConsole("Item removed successfully.", false);
        } catch (ItemNotFoundException e) {
            // If there is an error removing the item, print error message and wait for user input
            Tools.printToConsole("Error removing item: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Print success message and wait for user input
        Tools.waitForUser(input);
    }

    private static void promptEquipItem() {
        // Print the inventory
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);

        // Get user input for the ID of the item to equip
        int id = Tools.validateInt(input, "Enter the ID of the item you want to equip:");
        try {
            inventorySystem.equip(id); // Use the equip method from the inventory system to equip the item
        } catch (ItemNotFoundException | InvalidEquipSlotException e) {
            // If there is an error equipping the item, print error message and wait for user input
            Tools.printToConsole("Error equipping item: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
    }

    private static void promptUnequipItem() {
        // Print the equipment stats
        Tools.printToConsole(inventorySystem.getEquipmentStats(), true);
        // Get user input for the equip slot of the item to unequip
        EquipSlot equipSlot = Tools.validateEquipSlot(input, "Enter the equip slot of the item you want to unequip:");
        try {
            inventorySystem.unequip(equipSlot); // Use the unequip method from the inventory system to unequip the item
        } catch (InvalidEquipSlotException e) {
            // If there is an error unequipping the item, print error message and wait for user input
            Tools.printToConsole("Error unequipping item: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Print success message and wait for user input
        Tools.printToConsole("Item unequipped successfully.", false);
        Tools.waitForUser(input);
    }

    private static void promptManageInventory() {
        Tools.printToConsole("""
                =============== MANAGE INVENTORY ===============
                [1] Sort by Weight
                [2] Sort by Name
                [3] Sort by Type
                [0] Exit
                ===============================================
                """, true);
        int choice = Tools.validateInt(input, "Enter your choice");
        switch (choice) {
            case 1:
                inventorySystem.sortByWeight();
                break;
            case 2:
                inventorySystem.sortByName();
                break;
            case 3:
                inventorySystem.sortByType();
                break;
            case 0:
                break;
            default:
                Tools.printToConsole("Invalid choice. Please try again.", false);
                Tools.waitForUser(input);
                break;
        }
    }

    private static void promptExpandInventory() {
        int current = inventorySystem.getInventory().getUnlockedSlots();
        int max = inventorySystem.getInventory().getMaxSlots();

        Tools.printToConsole("=============== EXPAND INVENTORY ===============\n"
                + "Current unlocked slots: " + current + " / " + max + "\n\n"
                + "Are you sure you want to expand inventory with +32 slots?\n\n"
                + "[1] Yes\n"
                + "[2] No (Back to Main menu)\n"
                + "===============================================", true);

        int choice = Tools.validateInt(input, "Enter your choice");

        if (choice == 1) {
            inventorySystem.expandInventorySlots();

            int after = inventorySystem.getInventory().getUnlockedSlots();

            if (after == current) {
                Tools.printToConsole("Inventory is already at max slots: " + after, false);
            } else {
                Tools.printToConsole("Inventory expanded: " + current + " -> " + after, false);
            }

            Tools.waitForUser(input);
        }

        if (choice != 1 && choice != 2) {
            Tools.printToConsole("Invalid choice.", false);
            Tools.waitForUser(input);
        }
    }

    private static void promptFindItemById() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);

        // Get user input for the ID of the item to find
        int id = Tools.validateInt(input, "Enter the ID of the item you want to find:");

        // Initialize variables
        String itemName = "";
        int totalAmount = 0;

        // Get the item name from the master item list
        for (Item item : itemsList) {
            // If the item ID matches the ID to find, set the item name and break the loop
            if (item.getId() == id) {
                // Set the item name
                itemName = item.getName();
                // Break the loop
                break;
            }
        }

        // If item ID doesn't exist at all
        if (itemName.isEmpty()) {
            // If the item ID doesn't exist, print error message and wait for user input
            Tools.printToConsole("Item (" + id + ") does not exist.", false);
            Tools.waitForUser(input);
            return; // Exit the method if the item ID doesn't exist
        }

        // Get the total amount of the item from the inventory
        for (Item item : inventorySystem.getInventory().getSlots()) {
            // If the item ID matches the ID to find, add the quantity to the total amount
            if (item.getId() == id) {
                if (item instanceof Consumable consumable) {
                    // Add the quantity to the total amount
                    totalAmount += consumable.getQuantity();
                } else {
                    // Add 1 to the total amount
                    totalAmount++;
                }
            }
        }

        // Output result
        Tools.printToConsole("");
        // Print the item name and total amount
        Tools.printToConsole("Name: " + itemName, false);
        Tools.printToConsole("Amount carried: " + totalAmount, false);
        // Wait for user input
        Tools.waitForUser(input);
    }

    private static void printArmorsList() {
        Tools.printToConsole("============== ARMORS LIST ==============", false);
        for (Item item : itemsList) {
            if (item instanceof Armor) {
                Tools.printToConsole("#" + item.getId() + " - " + item.getName() + " - " + item.getWeight() + "kg"
                        + " - " + item.getType(), false);
            }
        }
        Tools.printToConsole("======================================", false);
    }

    private static void printWeaponsList() {
        Tools.printToConsole("============== WEAPONS LIST ==============", false);
        for (Item item : itemsList) {
            if (item instanceof Weapon) {
                Tools.printToConsole("#" + item.getId() + " - " + item.getName() + " - " + item.getWeight() + "kg"
                        + " - " + item.getType(), false);
            }
        }
        Tools.printToConsole("======================================", false);
    }

    private static void printConsumablesList() {
        Tools.printToConsole("============== CONSUMABLES LIST ==============", false);
        for (Item item : itemsList) {
            if (item instanceof Consumable) {
                Tools.printToConsole("#" + item.getId() + " - " + item.getName() + " - " + item.getWeight() + "kg"
                        + " - " + item.getType(), false);
            }
        }
    }

    private static Item getItemById(int id) throws ItemNotFoundException {
        // Get the item by ID from the master item list
        for (Item item : itemsList) {
            // If the item ID matches the ID to find, return the item
            if (item.getId() == id) {
                return item; // Return the item
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")"); // Throw an exception if the item is not found
    }
}