package models;

import enums.EquipSlot;
import enums.ItemType;

public class Armor extends Item {
    private final EquipSlot equipSlot;
    private final int defense;

    public Armor(int id, String name, double weight, EquipSlot equipSlot, int defense) {
        super(id, name, weight, ItemType.ARMOR);
        this.equipSlot = equipSlot;
        this.defense = defense;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public String shortInfo() {
        return super.shortInfo() + "  |  Equip Slot: " + equipSlot + "  |  Defense: " + defense;
    }

    @Override
    public String toString() {
        return "A;" + getId() + ";" + getName() + ";" + getWeight() + ";" + getEquipSlot() + ";" + getDefense();
    }

    @Override
    public String equipmentInfo() {
        return name + " |  Defense: " + defense;
    }
}
