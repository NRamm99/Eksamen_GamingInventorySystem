import java.io.IOException;
import java.util.List;

import enums.EquipSlot;
import enums.WeaponType;
import exceptions.InvalidEquipSlotException;
import exceptions.InventoryFullException;
import exceptions.ItemNotFoundException;
import exceptions.QuantityTooLowException;
import exceptions.StackLimitReachedException;
import exceptions.WeightLimitReachedException;
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
        }
        System.out.println("Inventory loaded successfully");
    }

    public double currentWeight() {
        double weight = 0.0;
        for (Item item : inventory.slots) {
            weight += item.getWeight();
        }
        return weight;
    }

    public boolean canAdd(Item item) throws WeightLimitReachedException, InventoryFullException {
        if (currentWeight() + item.getWeight() > inventory.maxWeight) {
            throw new WeightLimitReachedException("Weight limit reached: (" + inventory.maxWeight + "kg)");
        } else if (inventory.slots.size() >= inventory.maxSlots) {
            throw new InventoryFullException("Inventory is full: (" + inventory.maxSlots + " slots)");
        }
        return true;
    }

    public void add(Item item) throws StackLimitReachedException, QuantityTooLowException, WeightLimitReachedException,
            InventoryFullException {
        if (item instanceof Consumable consumable) {
            for (Item invItem : inventory.slots) {
                if (invItem instanceof Consumable invConsumable
                        && invConsumable.getName().equals(consumable.getName())
                        && invConsumable.getQuantity() < invConsumable.getMaxStack()) {

                    int spaceLeft = invConsumable.getMaxStack() - invConsumable.getQuantity();
                    int amountToTransfer = Math.min(consumable.getQuantity(), spaceLeft);

                    invConsumable.addQuantity(amountToTransfer);
                    consumable.removeQuantity(amountToTransfer);
                    return;
                }
            }
        }

        if (!canAdd(item)) {
            // canAdd will throw the appropriate exception
            return;
        }
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
    public void sortByWeight() {
        // PLACEHOLDER
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
            if (inventory.equippedItems.containsKey(equipSlot)) {
                throw new InvalidEquipSlotException("Equip slot already in use: (" + equipSlot + ")");
            }
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
        StringBuilder stats = new StringBuilder();
        stats.append("Inventory Stats:\n");
        stats.append("Items: " + inventory.slots.size() + " / " + inventory.unlockedSlots + "\n");
        stats.append("Weight: " + currentWeight() + "kg / " + inventory.maxWeight + "kg" + "\n");
        stats.append("Equipped Items: " + inventory.equippedItems.size() + "\n");
        for (EquipSlot equipSlot : inventory.equippedItems.keySet()) {
            stats.append(equipSlot + ": " + inventory.equippedItems.get(equipSlot).shortInfo() + "\n");
        }

        return stats.toString();
    }

    public void resetInventory() {
        inventory.slots.clear();
        inventory.equippedItems.clear();
        inventory.unlockedSlots = 32;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
