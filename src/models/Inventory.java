package models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import enums.EquipSlot;

public class Inventory {
    public final List<Item> slots = new ArrayList<>();
    public final Map<EquipSlot, Item> equippedItems = new EnumMap<>(EquipSlot.class);

    public int unlockedSlots = 32;
    public final int maxSlots = 192;
    public final double maxWeight = 50.0;

    public double currentWeight() {
        double weight = 0.0;
        for (Item item : slots) {
            if (item instanceof Consumable c) {
                weight += c.getWeight() * c.getQuantity();
            } else {
                weight += item.getWeight();
            }
        }

        return weight;
    }

    public String getInventoryStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("Inventory Stats:\n");
        stats.append("Slots Used: " + slots.size() + " / " + unlockedSlots + "\n");
        stats.append("Weight: " + currentWeight() + "kg / " + maxWeight + "kg" + "\n");
        stats.append("Equipped Items: " + equippedItems.size() + "\n");
        for (EquipSlot equipSlot : equippedItems.keySet()) {
            stats.append(equipSlot + ": " + equippedItems.get(equipSlot).shortInfo() + "\n");
        }

        return stats.toString();
    }

    public String getEquipmentStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("============== EQUIPMENT ==============\n");
        stats.append("Equipped Items: " + equippedItems.size() + "\n\n");

        // calculate total defense and damage
        int totalDefense = 0;
        int totalDamage = 0;
        for (Item item : equippedItems.values()) {
            if (item instanceof Weapon w) {
                totalDamage += w.getDamage();
            }
            if (item instanceof Armor a) {
                totalDefense += a.getDefense();
            }
        }
        // print total defense and damage
        stats.append("Total Defense: " + totalDefense + "\n");
        stats.append("Total Damage: " + totalDamage + "\n");
        stats.append("\n");

        // print equipment info for each slot
        for (EquipSlot slot : EquipSlot.values()) {

            Item item = equippedItems.get(slot);

            if (item == null) {
                stats.append(slot + ": <empty>\n");
            } else {
                stats.append(slot + ": " + item.equipmentInfo() + "\n");
            }
        }
        stats.append("======================================\n");
        return stats.toString();
    }

    @Override
    public String toString() {
        StringBuilder inventory = new StringBuilder();
        inventory.append("============== STATS ==============\n");
        inventory.append(getInventoryStats());
        inventory.append("=================== ITEMS ===================\n");
        for (Item item : slots) {
            inventory.append(item.shortInfo() + "\n");
        }
        inventory.append("======================================\n");
        return inventory.toString();
    }
}
