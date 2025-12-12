package models.items;

public class Item {

    private String itemID;
    private String itemName;
    private double itemWeight;

    public Item(String itemID, String itemName, double itemWeight) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return "Item ID: #" + itemID + " - " + itemName + " - Weight: " + itemWeight;
    }
}