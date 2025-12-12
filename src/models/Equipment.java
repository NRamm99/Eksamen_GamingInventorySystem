package models;

import models.items.Armor;
import models.items.Item;
import models.items.Weapon;
import models.items.enums.EquipSlot;

public class Equipment {
    private static final int HEAD_SLOT = 0;
    private static final int NECK_SLOT = 1;
    private static final int SHOULDERS_SLOT = 2;
    private static final int CHEST_SLOT = 3;
    private static final int WRISTS_SLOT = 4;
    private static final int HANDS_SLOT = 5;
    private static final int LEGS_SLOT = 6;
    private static final int FEET_SLOT = 7;
    private static final int MAIN_HAND_SLOT = 8;
    private static final int OFF_HAND_SLOT = 9;
    private static final int RANGED_SLOT = 10;

    private Item[] items;

    public Equipment() {
        this.items = new Item[11]; // 8 armor slots + 1 main hand, 1 off hand and 1 ranged slot
    }

    public double getWeight() {
        double weight = 0;
        for (Item item : items) {
            if (item != null) {
                weight += item.getItemWeight();
            }
        }
        return weight;
    }

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int slotIndex) {
        return items[slotIndex];
    }

    public boolean equipItem(Item item) {
        if (item instanceof Armor armor) {
            return equipArmor(armor);
        } else if (item instanceof Weapon weapon) {
            return equipWeapon(weapon);
        }
        return false;
    }

    private boolean equipArmor(Armor armor) {
        // Get the equip slot for the armor
        EquipSlot equipSlot = armor.getEquipSlot();

        // Get the slot index for the armor
        Integer slotIndex = getArmorSlotIndex(equipSlot);

        // Check if the slot index is valid and if the slot is already occupied
        if (slotIndex == null || isSlotOccupied(slotIndex)) {
            return false;
        }

        // Equip the armor
        items[slotIndex] = armor;
        return true;
    }

    private boolean equipWeapon(Weapon weapon) {
        // Get the equip slot for the weapon
        EquipSlot equipSlot = weapon.getEquipSlot();

        // Equip the weapon to the appropriate slot
        switch (equipSlot) {
            case MAIN_HAND:
                // Equip the weapon to the main hand slot
                return equipToSlot(weapon, MAIN_HAND_SLOT);
            case OFF_HAND:
                // Equip the weapon to the off hand slot
                return equipOffHand(weapon);
            case RANGED:
                // Equip the weapon to the ranged slot
                return equipToSlot(weapon, RANGED_SLOT);
            case TWO_HANDED:
                // Equip the weapon to the main hand slot if it is a two handed weapon
                return equipTwoHanded(weapon);
            default:
                return false;
        }
    }

    private boolean equipOffHand(Weapon weapon) {
        if (isSlotOccupied(OFF_HAND_SLOT)) {
            return false;
        }
        if (isMainHandTwoHanded()) {
            return false;
        }
        items[OFF_HAND_SLOT] = weapon;
        return true;
    }

    private boolean equipTwoHanded(Weapon weapon) {
        // Check if the main hand or off hand is already occupied
        if (isSlotOccupied(MAIN_HAND_SLOT) || isSlotOccupied(OFF_HAND_SLOT)) {
            return false;
        }

        // Equip the weapon to the main hand slot
        items[MAIN_HAND_SLOT] = weapon;
        return true;
    }

    private boolean equipToSlot(Item item, int slotIndex) {
        // Check if the slot is already occupied
        if (isSlotOccupied(slotIndex)) {
            return false;
        }

        // Equip the item to the slot
        items[slotIndex] = item;
        return true;
    }

    private boolean isSlotOccupied(int slotIndex) {
        return items[slotIndex] != null;
    }

    private boolean isMainHandTwoHanded() {
        // Check if the main hand is occupied and if the weapon is a two handed weapon
        return items[MAIN_HAND_SLOT] != null
                && ((Weapon) items[MAIN_HAND_SLOT]).getEquipSlot() == EquipSlot.TWO_HANDED;
    }

    private Integer getArmorSlotIndex(EquipSlot equipSlot) {
        switch (equipSlot) {
            case HEAD:
                return HEAD_SLOT;
            case NECK:
                return NECK_SLOT;
            case SHOULDERS:
                return SHOULDERS_SLOT;
            case CHEST:
                return CHEST_SLOT;
            case WRISTS:
                return WRISTS_SLOT;
            case HANDS:
                return HANDS_SLOT;
            case LEGS:
                return LEGS_SLOT;
            case FEET:
                return FEET_SLOT;
            default:
                return null;
        }
    }
}
