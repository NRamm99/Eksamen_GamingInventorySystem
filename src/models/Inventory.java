package models;

import models.items.Item;

public class Inventory {
    private static final int DEFAULT_SLOTS = 32;
    private static final int MAX_SLOTS = 192;

    private int currentSlots;
    private int maxSlots;

    private Item[] items;

    public Inventory() {
        this.currentSlots = DEFAULT_SLOTS;
        this.maxSlots = MAX_SLOTS;

        this.items = new Item[currentSlots];
    }

    public int getCurrentSlots() {
        return currentSlots;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public double getWeight() {
        double weight = 0;
        for (Item item : items) {
            if (item != null) {
                weight += item.getItemWeight();
            }
        }
        return weight;
    }

    public Item[] getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        int emptySlot = -1;
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i] == null) {
                emptySlot = i;
                break;
            }
        }

        if (emptySlot == -1) {
            return false;
        }

        items[emptySlot] = item;
        return true;
    }
}
