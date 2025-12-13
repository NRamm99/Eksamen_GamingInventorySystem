import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.EquipSlot;
import exceptions.InventoryFullException;
import exceptions.ItemNotFoundException;
import exceptions.QuantityTooLowException;
import exceptions.StackLimitReachedException;
import exceptions.WeightLimitReachedException;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import utils.FileHandler;
import utils.Tools;

public class Main {
    private static List<Item> itemsList = new ArrayList<>();
    private static InventorySystem inventorySystem = new InventorySystem();
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        initializeItemsList();

        while (true) {
            promptMainMenu();
        }
    }

    private static void saveInventory() {
        try {
            FileHandler.save(inventorySystem.getInventory());
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    private static void initializeItemsList() {
        // Consumables
        try {
            itemsList.add(new Consumable(1, "Health Potion", 0.5, 10, 10));
            itemsList.add(new Consumable(2, "Mana Potion", 0.5, 10, 10));
        } catch (Exception e) {
            System.out.println("Error adding consumables: " + e.getMessage());
        }
        // Weapons
        try {
            itemsList.add(new Weapon(3, "Sword", 2.0, EquipSlot.MAIN_HAND, 10));
            itemsList.add(new Weapon(4, "Axe", 3.0, EquipSlot.MAIN_HAND, 15));
        } catch (Exception e) {
            System.out.println("Error adding weapons: " + e.getMessage());
        }
        // Armor
        try {
            itemsList.add(new Armor(5, "Leather Armor", 2.0, EquipSlot.CHEST, 10));
            itemsList.add(new Armor(6, "Iron Armor", 3.0, EquipSlot.CHEST, 15));
        } catch (Exception e) {
            System.out.println("Error adding armor: " + e.getMessage());
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
                try {
                    promptAddItem();
                } catch (ItemNotFoundException | StackLimitReachedException | QuantityTooLowException
                        | WeightLimitReachedException | InventoryFullException e) {
                    Tools.printToConsole("Error adding item: " + e.getMessage(), false);
                    Tools.waitForUser(input);
                }
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
            case 0:
                saveInventory();
                Tools.printToConsole("Exiting program...", false);
                System.exit(0);
                break;
            default:
                Tools.printToConsole("Invalid choice. Please try again.", false);
                Tools.waitForUser(input);
                break;
        }
    }

    private static void promptViewInventory() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);
        Tools.waitForUser(input);

    }

    private static void promptViewEquipment() {
        Tools.printToConsole(inventorySystem.getEquipmentStats(), true);
        Tools.waitForUser(input);
    }

    private static void promptAddItem() throws ItemNotFoundException, StackLimitReachedException,
            QuantityTooLowException, WeightLimitReachedException, InventoryFullException {
        printItemsList();

        // IMPLEMENT ADD ITEM LOGIC HERE
        int id = Tools.validateInt(input, "Enter the ID of the item you want to add:");
        Item item = getItemById(id);

        if (item instanceof Consumable consumable) {
            int quantity = Tools.validateInt(input, "Enter the quantity of the item you want to add:");
            consumable.setQuantity(quantity);
            inventorySystem.add(consumable);
        } else {
            inventorySystem.add(item);
        }
        Tools.printToConsole("Item added successfully.", false);
        Tools.waitForUser(input);
    }

    private static Item getItemById(int id) throws ItemNotFoundException {
        for (Item item : itemsList) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")");
    }

    private static void promptRemoveItem() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);

        // IMPLEMENT REMOVE ITEM LOGIC HERE
        int id = Tools.validateInt(input, "Enter the ID of the item you want to remove:");
        try {
            inventorySystem.remove(id);
            Tools.printToConsole("Item removed successfully.", false);
        } catch (ItemNotFoundException e) {
            Tools.printToConsole("Error removing item: " + e.getMessage(), false);
        }
        Tools.waitForUser(input);
    }

    private static void promptEquipItem() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);
        Tools.waitForUser(input);

        // IMPLEMENT EQUIP ITEM LOGIC HERE
    }

    private static void promptUnequipItem() {
        Tools.printToConsole(inventorySystem.getEquipmentStats(), true);
        Tools.waitForUser(input);

        // IMPLEMENT UNEQUIP ITEM LOGIC HERE
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
        int choice = input.nextInt();
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

    private static void printItemsList() {
        Tools.printToConsole("============== ITEMS LIST ==============", false);
        for (Item item : itemsList) {
            Tools.printToConsole("#" + item.getId() + " - " + item.getName() + " - " + item.getWeight() + "kg" + " - " + item.getType(), false);
        }
        Tools.printToConsole("======================================", false);
    }
}