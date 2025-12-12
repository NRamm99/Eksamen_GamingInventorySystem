package models;


import controllers.StorageController;
import models.items.Item;

public class Player {

    private String playerName;

    private StorageController storageController;

    public Player(String playerName) {
        this.playerName = playerName;

        this.storageController = new StorageController();
    }

    public String getPlayerName() {
        return playerName;
    }

    public Item[] getEquipment() {
        return storageController.getEquipment();
    }

    public double getEquipmentWeight() {
        return storageController.getEquipmentWeight();
    }

    public double getTotalWeight() {
        return (storageController.getInventoryWeight() + storageController.getEquipmentWeight());
    }

    public Item[] getInventory() {
        return storageController.getInventory();
    }
}
