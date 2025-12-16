package utils;

import java.util.ArrayList;
import java.util.List;

import enums.EquipSlot;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

public class ItemInitializer {

    public static List<Item> initializeItemsList() {
        List<Item> itemsList = new ArrayList<>();
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
        return itemsList;
    }
}
