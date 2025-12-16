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
            itemsList.add(new Consumable(1, "Health Potion", 0.2, 1, 3));
            itemsList.add(new Consumable(2, "Mana Potion", 0.2, 1, 3));
        } catch (Exception e) {
            System.out.println("Error adding consumables: " + e.getMessage());
        }
        // Weapons
        try {
            // Main_Hand
            itemsList.add(new Weapon(3, "Sword", 2.0, EquipSlot.MAIN_HAND, 10, false));
            itemsList.add(new Weapon(4, "Axe", 3.0, EquipSlot.MAIN_HAND, 15, false));
            // Off_Hand
            itemsList.add(new Weapon(7, "Dagger", 1.0, EquipSlot.OFF_HAND, 5, false));
            // Ranged
            itemsList.add(new Weapon(5, "Bow", 2.0, EquipSlot.RANGED, 10, false));
            itemsList.add(new Weapon(6, "Crossbow", 3.0, EquipSlot.RANGED, 15, false));
            // Two_Handed
            itemsList.add(new Weapon(8, "Greatsword", 5.0, EquipSlot.MAIN_HAND, 20, true));
        } catch (Exception e) {
            System.out.println("Error adding weapons: " + e.getMessage());
        }
        // Armor
        try {
            // Head
            itemsList.add(new Armor(9, "Leather Helmet", 1.0, EquipSlot.HEAD, 5));
            itemsList.add(new Armor(10, "Iron Helmet", 2.0, EquipSlot.HEAD, 10));
            // Neck
            itemsList.add(new Armor(11, "Leather Necklace", 0.5, EquipSlot.NECK, 3));
            itemsList.add(new Armor(12, "Iron Necklace", 1.0, EquipSlot.NECK, 6));
            // Shoulders
            itemsList.add(new Armor(13, "Leather Shoulders", 1.5, EquipSlot.SHOULDERS, 7));
            itemsList.add(new Armor(14, "Iron Shoulders", 3.0, EquipSlot.SHOULDERS, 14));
            // Chest
            itemsList.add(new Armor(15, "Leather Armor", 2.0, EquipSlot.CHEST, 10));
            itemsList.add(new Armor(16, "Iron Armor", 3.0, EquipSlot.CHEST, 15));
            // Wrists
            itemsList.add(new Armor(17, "Leather Bracers", 1.0, EquipSlot.WRISTS, 5));
            itemsList.add(new Armor(18, "Iron Bracers", 2.0, EquipSlot.WRISTS, 10));
            // Hands
            itemsList.add(new Armor(19, "Leather Gloves", 1.5, EquipSlot.HANDS, 7));
            itemsList.add(new Armor(20, "Iron Gloves", 3.0, EquipSlot.HANDS, 14));
            // Legs
            itemsList.add(new Armor(21, "Leather Pants", 2.5, EquipSlot.LEGS, 12));
            itemsList.add(new Armor(22, "Iron Pants", 5.0, EquipSlot.LEGS, 24));
            // Feet
            itemsList.add(new Armor(23, "Leather Boots", 1.5, EquipSlot.FEET, 7));
            itemsList.add(new Armor(24, "Iron Boots", 3.0, EquipSlot.FEET, 14));
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
                findItemById();
                break;
            case 10:
                resetInventory();
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

    private static void resetInventory() {
        inventorySystem.resetInventory();
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
        printArmorsList();
        int id = Tools.validateInt(input, "Enter the ID of the armor you want to add:");
        Item item = null;
        // Get item by ID
        try {
            item = getItemById(id);
        } catch (ItemNotFoundException e) {
            Tools.printToConsole("Item not found: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Check if item is an armor
        if (!(item instanceof Armor)) {
            Tools.printToConsole("Item is not a armor.", false);
            Tools.waitForUser(input);
            return;
        }
        // Validate quantity
        int quantity = Tools.validateInt(input, "Enter the quantity of the armor you want to add:");
        // Add armor to inventory
        for (int i = 0; i < quantity; i++) {
            try {
                inventorySystem.add(item);
            } catch (StackLimitReachedException | QuantityTooLowException | WeightLimitReachedException
                    | InventoryFullException e) {
                Tools.printToConsole("Error adding item: " + e.getMessage(), false);
                Tools.waitForUser(input);
            }
        }
        Tools.printToConsole("Armor added successfully.", false);
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

    private static void promptAddWeapon() {
        printWeaponsList();
        int id = Tools.validateInt(input, "Enter the ID of the weapon you want to add:");
        Item item = null;
        // Get item by ID
        try {
            item = getItemById(id);
        } catch (ItemNotFoundException e) {
            Tools.printToConsole("Item not found: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
        // Check if item is a weapon
        if (!(item instanceof Weapon weapon)) {
            Tools.printToConsole("Item is not a weapon.", false);
            Tools.waitForUser(input);
            return;
        }
        // Validate quantity
        int quantity = Tools.validateInt(input,
                "Enter the quantity of the weapon you want to add");
        // Add weapon to inventory
        for (int i = 0; i < quantity; i++) {
            try {
                inventorySystem.add(weapon);
            } catch (StackLimitReachedException | QuantityTooLowException | WeightLimitReachedException
                    | InventoryFullException e) {
                Tools.printToConsole("Error adding item: " + e.getMessage(), false);
                Tools.waitForUser(input);
            }
        }
        Tools.printToConsole("Weapon added successfully.", false);
        Tools.waitForUser(input);
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

        // IMPLEMENT EQUIP ITEM LOGIC HERE
        int id = Tools.validateInt(input, "Enter the ID of the item you want to equip:");
        try {
            inventorySystem.equip(id);
        } catch (ItemNotFoundException | InvalidEquipSlotException e) {
            Tools.printToConsole("Error equipping item: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
    }

    private static void promptUnequipItem() {
        Tools.printToConsole(inventorySystem.getEquipmentStats(), true);
        EquipSlot equipSlot = Tools.validateEquipSlot(input, "Enter the equip slot of the item you want to unequip:");
        try {
            inventorySystem.unequip(equipSlot);
        } catch (InvalidEquipSlotException e) {
            Tools.printToConsole("Error unequipping item: " + e.getMessage(), false);
            Tools.waitForUser(input);
        }
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
        int current = inventorySystem.getInventory().unlockedSlots;
        int max = inventorySystem.getInventory().maxSlots;

        Tools.printToConsole("=============== EXPAND INVENTORY ===============\n"
                + "Current unlocked slots: " + current + " / " + max + "\n\n"
                + "Are you sure you want to expand inventory with +32 slots?\n\n"
                + "[1] Yes\n"
                + "[2] No (Back to Main menu)\n"
                + "===============================================", true);

        int choice = Tools.validateInt(input, "Enter your choice");

        if (choice == 1) {
            inventorySystem.expandInventorySlots();

            int after = inventorySystem.getInventory().unlockedSlots;

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

    private static void findItemById() {
        Tools.printToConsole(inventorySystem.getInventory().toString(), true);

        int id = Tools.validateInt(input, "Enter the ID of the item you want to find:");

        String itemName = "";
        int totalAmount = 0;

        // Get the item name from the master item list
        for (Item item : itemsList) {
            if (item.getId() == id) {
                itemName = item.getName();
                break;
            }
        }

        // If item ID doesn't exist at all
        if (itemName.isEmpty()) {
            Tools.printToConsole("Item (" + id + ") does not exist.", false);
            Tools.waitForUser(input);
            return;
        }

        for (Item item : inventorySystem.getInventory().slots) {
            if (item.getId() == id) {
                if (item instanceof Consumable consumable) {
                    totalAmount += consumable.getQuantity();
                } else {
                    totalAmount++;
                }
            }
        }

        // Output result
        Tools.printToConsole("");
        Tools.printToConsole("Name: " + itemName, false);
        Tools.printToConsole("Amount carried: " + totalAmount, false);
        Tools.waitForUser(input);
    }
}