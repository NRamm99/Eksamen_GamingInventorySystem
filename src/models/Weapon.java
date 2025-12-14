package models;

import enums.EquipSlot;
import enums.ItemType;

public class Weapon extends Item {
    private final EquipSlot equipSlot;
    private final int damage;
    private final boolean isTwoHanded;

    public Weapon(int id, String name, double weight, EquipSlot equipSlot, int damage, boolean isTwoHanded) {
        super(id, name, weight, ItemType.WEAPON);
        this.equipSlot = equipSlot;
        this.damage = damage;
        this.isTwoHanded = isTwoHanded;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isTwoHanded() {
        return isTwoHanded;
    }

    @Override
    public String shortInfo() {
        return super.shortInfo() + "  |  Weapon Type: " + equipSlot + "  |  Damage: " + damage;
    }

    @Override
    public String toString() {
        if (isTwoHanded) {
            return "W;" + getId() + ";" + getName() + ";" + getWeight() + ";" + getEquipSlot() + ";" + getDamage()
                    + "Two-Handed";
        }
        return "W;" + getId() + ";" + getName() + ";" + getWeight() + ";" + getEquipSlot() + ";" + getDamage()
                + "One-Handed";
    }

    @Override
    public String equipmentInfo() {
        return name + " |  Damage: " + damage;
    }
}
