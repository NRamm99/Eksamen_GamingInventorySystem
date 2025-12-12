package controllers;

import models.Equipment;
import models.Inventory;
import models.items.Item;

public class StorageController {
    private static final double MAX_WEIGHT = 50;

    private Inventory inventory;
    private Equipment equipment;

    public StorageController() {
        this.inventory = new Inventory();
        this.equipment = new Equipment();
    }

    public boolean addItem(Item item) {
        if (validateWeight(item)) {
            return inventory.addItem(item);
        }
        return false;
    }

    public boolean validateWeight(Item item) {
        return (inventory.getWeight() + equipment.getWeight()) + item.getItemWeight() <= MAX_WEIGHT;
    }

    public Item[] getEquipment() {
        return equipment.getItems();
    }

    public double getEquipmentWeight() {
        return equipment.getWeight();
    }

    public double getInventoryWeight() {
        return inventory.getWeight();
    }

    public Item[] getInventory() {
        return inventory.getItems();
    }
}
