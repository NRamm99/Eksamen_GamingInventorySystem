package models;

import enums.ItemType;
import exceptions.QuantityTooLowException;
import exceptions.StackLimitReachedException;

public class Consumable extends Item {
    private int quantity;
    private final int maxStack;

    public Consumable(int id, String name, double weight, int quantity, int maxStack) {
        super(id, name, weight, ItemType.CONSUMABLE);
        this.quantity = quantity;
        this.maxStack = maxStack;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int spaceLeft() {
        return maxStack - quantity;
    }

    public void addQuantity(int amount) throws StackLimitReachedException {
        if (quantity + amount > maxStack) {
            throw new StackLimitReachedException("Cannot add more than the max stack size: (" + maxStack + ")");
        }
        quantity += amount;
    }

    public void removeQuantity(int amount) throws QuantityTooLowException {
        if (quantity - amount < 0) {
            throw new QuantityTooLowException("Cannot remove more than the quantity: (" + quantity + ")");
        }
        quantity -= amount;
    }

    @Override
    public String shortInfo() {
        return super.shortInfo() + "  |  Quantity: " + quantity + "  |  Max Stack: " + maxStack;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
