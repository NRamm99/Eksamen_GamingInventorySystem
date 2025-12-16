package models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import enums.EquipSlot;

public class Inventory {
    public static final int DEFAULT_UNLOCKED_SLOTS = 32; // 32 slots
    public static final int MAX_SLOTS = 192; // 192 slots
    public static final double MAX_WEIGHT = 50.0; // 50kg

    private List<Item> slots = new ArrayList<>();
    private Map<EquipSlot, Item> equippedItems = new EnumMap<>(EquipSlot.class);

    private int unlockedSlots = DEFAULT_UNLOCKED_SLOTS;
    private int maxSlots = MAX_SLOTS;
    private double maxWeight = MAX_WEIGHT;

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

    public List<Item> getSlots() {
        return slots;
    }

    public void setSlots(List<Item> array) {
        slots = array;
    }

    public int getUnlockedSlots() {
        return unlockedSlots;
    }

    public void setUnlockedSlots(int unlockedSlots) {
        this.unlockedSlots = unlockedSlots;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public Map<EquipSlot, Item> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(Map<EquipSlot, Item> equippedItems) {
        this.equippedItems = equippedItems;
    }

}
