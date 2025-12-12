package models.items;

public class Consumable extends Item {
    private int maxStack;


    public Consumable(String itemID, String itemName, double itemWeight, int maxStack) {
        super(itemID, itemName, itemWeight);
        this.maxStack = maxStack;
    }

    public int getMaxStack() {
        return maxStack;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
