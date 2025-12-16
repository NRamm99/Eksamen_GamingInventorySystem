import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enums.EquipSlot;
import exceptions.*;
import models.Armor;
import models.Consumable;
import models.Inventory;
import models.Item;
import models.Weapon;
import utils.FileHandler;

public class InventorySystem {
    private Inventory inventory;

    public InventorySystem() {
        try {
            this.inventory = FileHandler.load();
        } catch (IOException e) {
            this.inventory = new Inventory();
            System.out.println("Error loading inventory: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("Corrupt inventory data: " + e.getMessage());
            System.out.println("Fix: 'data/inventory.txt' and restart the program ");
            System.exit(1);
        }
        System.out.println("Inventory loaded successfully");
    }

    public void canAdd(Item item) throws WeightLimitReachedException, InventoryFullException {
        if (inventory.currentWeight() + item.getWeight() > inventory.maxWeight) {
            throw new WeightLimitReachedException("Weight limit reached: (" + inventory.maxWeight + "kg)");
        } else if (inventory.slots.size() >= inventory.unlockedSlots) {
            throw new InventoryFullException("Inventory is full: (" + inventory.unlockedSlots + " slots unlocked)");
        }
    }

    public void add(Item item) throws StackLimitReachedException, QuantityTooLowException, WeightLimitReachedException,
            InventoryFullException {

        if (item instanceof Consumable consumable) {

            // Fills out existing stacks

            for (Item invItem : inventory.slots) {

                if (invItem instanceof Consumable invConsumable
                        && invConsumable.getName().equals(consumable.getName())
                        && invConsumable.getQuantity() < invConsumable.getMaxStack()) {

                    int spaceLeft = invConsumable.getMaxStack() - invConsumable.getQuantity();
                    int amountToTransfer = Math.min(consumable.getQuantity(), spaceLeft);

                    invConsumable.addQuantity(amountToTransfer);
                    consumable.removeQuantity(amountToTransfer);
                }
            }

            // Creates new stack if there is leftovers
            while (consumable.getQuantity() > 0) {
                int stackAmount = Math.min(consumable.getQuantity(), consumable.getMaxStack());

                Consumable newStack = new Consumable(
                        consumable.getId(),
                        consumable.getName(),
                        consumable.getWeight(),
                        stackAmount,
                        consumable.getMaxStack());

                canAdd(newStack);

                inventory.slots.add(newStack);
                consumable.removeQuantity(stackAmount);
            }
            return;
        }

        canAdd(item);
        inventory.slots.add(item);
    }

    public void remove(int id) throws ItemNotFoundException {
        for (Item item : inventory.slots) {
            if (item.getId() == id) {
                inventory.slots.remove(item);
                return;
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")");
    }

    // public List<Item> search(String PLACEHOLDER) {}

    // -------- SORTS --------
    // Bubble sort for item weight of List<Item>
    // ArrayList that contains references to items
    // Array ───────────────▶ List
    //            ├── ref → Item A
    //            ├── ref → Item B
    //            └── ref → Item C
    public void sortByWeight() {
        List<Item> array = inventory.slots;
        // Array size (unsorted range)
        int size = array.size();
        boolean swapped;

        do {
            swapped = false;

            for (int i = 0; i < size - 1; i++) {
                // Compares the two getWeight() values from Item
                if (array.get(i).getWeight() > array.get(i + 1).getWeight()) {
                    // Temporarily store the current item references as temp
                    Item temp = array.get(i);
                    // Swap array[i] and array[i + 1] [references]
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);

                    swapped = true;
                }
            }
            // Unsorted part of the list
            size -= 1;
        } while (swapped);
    }

    public void sortByName() {
        // PLACEHOLDER
    }

    public void sortByType() {
        // PLACEHOLDER
    }

    // -------- EQUIPMENT --------
    public void equip(int id) throws ItemNotFoundException, InvalidEquipSlotException {
        Item item = getItemById(id);

        if (item instanceof Weapon weapon) {
            EquipSlot equipSlot = weapon.getEquipSlot();

            // Checks if the equip slot is already in use
            if (inventory.equippedItems.containsKey(equipSlot)) {
                throw new InvalidEquipSlotException("Equip slot already in use: (" + equipSlot + ")");
            }
            // Checks if the weapon is two handed and the off hand is already in use
            if (weapon.isTwoHanded() && (inventory.equippedItems.containsKey(EquipSlot.OFF_HAND))) {
                throw new InvalidEquipSlotException("Player is already holding a weapon in the off hand");
            }

            // Checks if the weapon is OFF_HAND
            if (equipSlot == EquipSlot.OFF_HAND) {
                // Checks if the player is already holding a two handed weapon in the main hand
                if (inventory.equippedItems.containsKey(EquipSlot.MAIN_HAND)) {
                    Weapon mainHand = (Weapon) inventory.equippedItems.get(EquipSlot.MAIN_HAND);
                    if (mainHand.isTwoHanded()) {
                        throw new InvalidEquipSlotException("Player is already holding a two handed weapon.");
                    }
                }
            }

            // Equips the weapon
            inventory.equippedItems.put(equipSlot, weapon);
            inventory.slots.remove(item);
            return;
        }

        if (item instanceof Armor armor) {
            EquipSlot equipSlot = armor.getEquipSlot();
            if (inventory.equippedItems.containsKey(equipSlot)) {
                throw new InvalidEquipSlotException("Equip slot already in use: (" + equipSlot + ")");
            }
            inventory.equippedItems.put(equipSlot, armor);
            inventory.slots.remove(item);
        }
    }

    public void unequip(EquipSlot equipSlot) throws InvalidEquipSlotException {
        if (!inventory.equippedItems.containsKey(equipSlot)) {
            throw new InvalidEquipSlotException("Equip slot not in use: (" + equipSlot + ")");
        }
        Item item = inventory.equippedItems.get(equipSlot);
        inventory.equippedItems.remove(equipSlot);
        inventory.slots.add(item);
    }

    private Item getItemById(int id) throws ItemNotFoundException {
        for (Item item : inventory.slots) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")");
    }

    public String getInventoryStats() {
        return inventory.getInventoryStats();
    }

    public void resetInventory() {
        inventory.slots.clear();
        inventory.equippedItems.clear();
        inventory.unlockedSlots = 32;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getEquipmentStats() {
        return inventory.getEquipmentStats();
    }

    public void expandInventorySlots() {
        if (inventory.unlockedSlots < inventory.maxSlots) {
            inventory.unlockedSlots += 32;
        }
    }
}
