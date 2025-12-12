package models.items;

import models.items.enums.EquipSlot;
import models.items.enums.WeaponType;

public class Weapon extends Item {

    private EquipSlot equipSlot;
    private WeaponType weaponType;
    private int weaponDamage;
    private int weaponRange;

    private int itemStrength;
    private int itemStamina;
    private int itemIntelligence;
    private int itemAgility;

    public Weapon(String itemID, String itemName, double itemWeight, int itemStrength, int itemStamina,
            int itemIntelligence, int itemAgility, EquipSlot equipSlot, WeaponType weaponType, int weaponDamage,
            int weaponRange) {

        super(itemID, itemName, itemWeight);
        this.itemStrength = itemStrength;
        this.itemStamina = itemStamina;
        this.itemIntelligence = itemIntelligence;
        this.itemAgility = itemAgility;
        this.equipSlot = equipSlot;
        this.weaponType = weaponType;
        this.weaponDamage = weaponDamage;
        this.weaponRange = weaponRange;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public int getWeaponRange() {
        return weaponRange;
    }

    @Override
    public String toString() {
        return super.toString() + " - Strength: " + itemStrength + " - Stamina: " + itemStamina + " - Intelligence: " + itemIntelligence + " - Agility: " + itemAgility + " - Equip Slot: " + equipSlot + " - Weapon Type: " + weaponType + " - Weapon Damage: "
                + weaponDamage + " - Weapon Range: " + weaponRange;
    }
}
