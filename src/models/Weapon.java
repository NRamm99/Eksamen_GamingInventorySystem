package models;

import enums.EquipSlot;
import enums.ItemType;

public class Weapon extends Item {
    private final EquipSlot equipSlot;
    private final int damage;

    public Weapon(int id, String name, double weight, EquipSlot equipSlot, int damage) {
        super(id, name, weight, ItemType.WEAPON);
        this.equipSlot = equipSlot;
        this.damage = damage;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String shortInfo() {
        return super.shortInfo() + "  |  Weapon Type: " + equipSlot + "  |  Damage: " + damage;
    }
}
