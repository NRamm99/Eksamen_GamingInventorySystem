package models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import enums.EquipSlot;

public class Inventory {
    public final List<Item> slots = new ArrayList<>();
    public final Map<EquipSlot, Item> equippedItems = new EnumMap<>(EquipSlot.class);

    public int unlockedSlots = 32;
    public final int maxSlots = 192;
    public final double maxWeight = 50.0;
}
