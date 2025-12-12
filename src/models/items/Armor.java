package models.items;

import models.items.enums.EquipSlot;

public class Armor extends Item {
    private EquipSlot equipSlot;
    private int armorRating;
    private int itemStrength;
    private int itemStamina;
    private int itemIntelligence;
    private int itemAgility;

    public Armor(String itemID, String itemName, double itemWeight, int itemStrength, int itemStamina,
            int itemIntelligence, int itemAgility, EquipSlot equipSlot, int armorRating) {
        super(itemID, itemName, itemWeight);
        this.equipSlot = equipSlot;
        this.armorRating = armorRating;
        this.itemStrength = itemStrength;
        this.itemStamina = itemStamina;
        this.itemIntelligence = itemIntelligence;
        this.itemAgility = itemAgility;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public int getArmorRating() {
        return armorRating;
    }

    @Override
    public String toString() {
        return super.toString() + " - Strength: " + itemStrength + " - Stamina: " + itemStamina + " - Intelligence: " + itemIntelligence + " - Agility: " + itemAgility + " - Equip Slot: " + equipSlot + " - Armor: " + armorRating;
    }
}
