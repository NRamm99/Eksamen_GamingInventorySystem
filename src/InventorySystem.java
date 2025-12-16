import java.io.IOException;
import java.util.List;

import enums.EquipSlot;
import exceptions.*;
import models.Armor;
import models.Consumable;
import models.Inventory;
import models.Item;
import models.Weapon;
import utils.BubbleSort;
import utils.FileHandler;

public class InventorySystem {
    private Inventory inventory;

    public InventorySystem() {
        try {
            this.inventory = FileHandler.load(); // Load the inventory from the file
        } catch (IOException e) {
            this.inventory = new Inventory(); // Create a new inventory if there is an error loading the inventory from
                                              // the file
            System.out.println("Error loading inventory: " + e.getMessage());
        } catch (InvalidQuantityException e) {
            System.out.println("Corrupt inventory data: " + e.getMessage()); // Print an error message if the inventory
                                                                             // data is corrupt
            System.out.println("Fix: 'data/inventory.txt' and restart the program "); // Print a message to fix the
                                                                                      // inventory data
            System.exit(1);
        }
        // Print a success message if the inventory is loaded successfully
        System.out.println("Inventory loaded successfully");
    }

    public void canAdd(Item item) throws WeightLimitReachedException, InventoryFullException {
        // Check if the item weight is too heavy
        if (inventory.currentWeight() + item.getWeight() > inventory.getMaxWeight()) {
            // Throw an exception if the item weight is too heavy
            throw new WeightLimitReachedException("Weight limit reached: (" + inventory.getMaxWeight() + "kg)");
            // Check if the inventory is full
        } else if (inventory.getSlots().size() >= inventory.getUnlockedSlots()) {
            throw new InventoryFullException(
                    // Throw an exception if the inventory is full
                    "Inventory is full: (" + inventory.getUnlockedSlots() + " slots unlocked)");
        } // If the item weight is not too heavy and the inventory is not full, return
    }

    public void add(Item item) throws StackLimitReachedException, QuantityTooLowException, WeightLimitReachedException,
            InventoryFullException {

        // Check if the item is a consumable
        if (item instanceof Consumable consumable) {

            // Loop through the inventory slots
            for (Item invItem : inventory.getSlots()) {

                // Check if the inventory item is the same as the consumable and the quantity is
                // less than the max stack and the ID is the same
                if (invItem instanceof Consumable invConsumable
                        && invConsumable.getId() == consumable.getId()
                        && invConsumable.getQuantity() < invConsumable.getMaxStack()) {

                    // Calculate the space left in the inventory item
                    int spaceLeft = invConsumable.getMaxStack() - invConsumable.getQuantity();

                    // Calculate the amount to transfer
                    int amountToTransfer = Math.min(consumable.getQuantity(), spaceLeft);

                    invConsumable.addQuantity(amountToTransfer);
                    consumable.removeQuantity(amountToTransfer); // Remove the quantity from the consumable
                }
            }

            // Creates new stack if there is leftovers
            while (consumable.getQuantity() > 0) { // Loop through the consumable quantity
                int stackAmount = Math.min(consumable.getQuantity(), consumable.getMaxStack());

                Consumable newStack = new Consumable( // Create a new consumable stack
                        consumable.getId(),
                        consumable.getName(),
                        consumable.getWeight(),
                        stackAmount, // Set the quantity of the new stack
                        consumable.getMaxStack()); // Set the max stack of the new stack

                canAdd(newStack); // Check if the new stack can be added to the inventory

                inventory.getSlots().add(newStack); // Add the new stack to the inventory
                consumable.removeQuantity(stackAmount); // Remove the quantity from the consumable
            }
            return; // Exit the method if the consumable is added to the inventory
        }

        canAdd(item); // Check if the item can be added to the inventory
        inventory.getSlots().add(item); // Add the item to the inventory
    }

    public void remove(int id) throws ItemNotFoundException {
        for (Item item : inventory.getSlots()) { // Loop through the inventory slots
            if (item.getId() == id) {
                inventory.getSlots().remove(item); // Remove the item from the inventory
                return; // Exit the method if the item is found and removed from the inventory
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")"); // Throw an exception if the item is not found
    }

    // -------- SORTS --------
    public void sortByWeight() {
        List<Item> tempSlots = BubbleSort.sortByWeight(inventory.getSlots()); // Sort the inventory by weight
        inventory.setSlots(tempSlots); // Set the sorted inventory slots
    }

    public void sortByName() {
        List<Item> tempSlots = BubbleSort.sortByName(inventory.getSlots());
        inventory.setSlots(tempSlots);
    }

    public void sortByType() {
        List<Item> tempSlots = BubbleSort.sortByType(inventory.getSlots());
        inventory.setSlots(tempSlots);
    }

    // -------- EQUIPMENT --------
    public void equip(int id) throws ItemNotFoundException, InvalidEquipSlotException {
        Item item = getItemById(id); // Get the item by ID from the inventory

        if (item instanceof Weapon weapon) { // Check if the item is a weapon
            EquipSlot equipSlot = weapon.getEquipSlot(); // Get the equip slot from the weapon

            // Checks if the equip slot is already in use
            if (inventory.getEquippedItems().containsKey(equipSlot)) {
                throw new InvalidEquipSlotException("Equip slot already in use: (" + equipSlot + ")");
            }
            // Checks if the weapon is two handed and the off hand is already in use
            if (weapon.isTwoHanded() && (inventory.getEquippedItems().containsKey(EquipSlot.OFF_HAND))) {
                throw new InvalidEquipSlotException("Player is already holding a weapon in the off hand");
            }

            // Checks if the weapon is OFF_HAND
            if (equipSlot == EquipSlot.OFF_HAND) {
                // Checks if the player is already holding a two handed weapon in the main hand
                if (inventory.getEquippedItems().containsKey(EquipSlot.MAIN_HAND)) {
                    Weapon mainHand = (Weapon) inventory.getEquippedItems().get(EquipSlot.MAIN_HAND);
                    if (mainHand.isTwoHanded()) {
                        throw new InvalidEquipSlotException("Player is already holding a two handed weapon.");
                    }
                }
            }

            // Equips the weapon
            inventory.getEquippedItems().put(equipSlot, weapon);
            inventory.getSlots().remove(item);
            return;
        }

        if (item instanceof Armor armor) { // Check if the item is a armor
            EquipSlot equipSlot = armor.getEquipSlot();
            // Check if the equip slot is already in use
            if (inventory.getEquippedItems().containsKey(equipSlot)) {
                // Throw an exception if the equip slot is already in use
                throw new InvalidEquipSlotException("Equip slot already in use: (" + equipSlot + ")");
            }
            // Equip the armor
            inventory.getEquippedItems().put(equipSlot, armor);
            // Remove the item from the inventory
            inventory.getSlots().remove(item);
        }
    }

    public void unequip(EquipSlot equipSlot) throws InvalidEquipSlotException {
        // Check if the equip slot is not in use
        if (!inventory.getEquippedItems().containsKey(equipSlot)) {
            // Throw an exception if the equip slot is not in use
            throw new InvalidEquipSlotException("Equip slot not in use: (" + equipSlot + ")");
        }
        // Get the item from the equipped items
        Item item = inventory.getEquippedItems().get(equipSlot);
        // Remove the item from the equipped items
        inventory.getEquippedItems().remove(equipSlot);
        // Add the item to the inventory
        inventory.getSlots().add(item);
    }

    private Item getItemById(int id) throws ItemNotFoundException {
        for (Item item : inventory.getSlots()) { // Loop through the inventory slots
            if (item.getId() == id) {
                return item; // Return the item if the ID matches
            }
        }
        throw new ItemNotFoundException("Item not found: (" + id + ")"); // Throw an exception if the item is not found
    }

    public String getInventoryStats() {
        return inventory.getInventoryStats(); // Return the inventory stats
    }

    public void resetInventory() {
        // Clear the inventory slots
        inventory.getSlots().clear();

        // Clear the equipped items
        inventory.getEquippedItems().clear();

        // Set the unlocked slots to the default unlocked slots
        inventory.setUnlockedSlots(Inventory.DEFAULT_UNLOCKED_SLOTS);
    }

    public Inventory getInventory() {
        return inventory; // Return the inventory
    }

    public String getEquipmentStats() {
        return inventory.getEquipmentStats(); // Return the equipment stats
    }

    public void expandInventorySlots() {
        // Check if the unlocked slots are less than the max slots
        if (inventory.getUnlockedSlots() < inventory.getMaxSlots()) {
            // Set the unlocked slots to the default unlocked slots
            inventory.setUnlockedSlots((inventory.getUnlockedSlots() + Inventory.DEFAULT_UNLOCKED_SLOTS));
        }
        // If the unlocked slots are not less than the max slots, do nothing
    }
}
