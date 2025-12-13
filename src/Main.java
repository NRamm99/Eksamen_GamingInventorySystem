import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enums.EquipSlot;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import utils.FileHandler;

public class Main {
    private static List<Item> itemsList = new ArrayList<>();
    private static InventorySystem inventorySystem = new InventorySystem();

    public static void main(String[] args) {
        System.out.println(inventorySystem.getInventoryStats());
        initializeItemsList();
        saveInventory();
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
}