package utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import enums.EquipSlot;
import models.Armor;
import models.Consumable;
import models.Inventory;
import models.Item;
import models.Weapon;

public class FileHandler {
    private static final String INVENTORY_PATH = "data/inventory.txt";

    public static void save(Inventory inv) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(INVENTORY_PATH))) {
            w.write("SLOTS;" + inv.unlockedSlots);
            w.newLine();

            for (Item i : inv.slots) {
                if (i instanceof Weapon we)
                    w.write("W;" + we.getId() + ";" + we.getName() + ";" + we.getWeight() + ";" + we.getEquipSlot()
                            + ";" + we.getDamage());
                if (i instanceof Armor a)
                    w.write("A;" + a.getId() + ";" + a.getName() + ";" + a.getWeight() + ";" + a.getEquipSlot() + ";"
                            + a.getDefense());
                if (i instanceof Consumable c)
                    w.write("C;" + c.getId() + ";" + c.getName() + ";" + c.getWeight() + ";" + c.getQuantity() + ";"
                            + c.getMaxStack());
                w.newLine();
            }

            for (var e : inv.equippedItems.entrySet()) {
                w.write("E;" + e.getKey() + ";" + e.getValue());
                w.newLine();
            }
        }
    }

    public static Inventory load() throws IOException {
        Inventory inv = new Inventory();
        List<String> lines = Files.readAllLines(Paths.get(INVENTORY_PATH));

        for (String l : lines) {
            String[] p = l.split(";");
            switch (p[0]) {
                case "SLOTS" -> inv.unlockedSlots = Integer.parseInt(p[1]);
                case "W" -> inv.slots.add(new Weapon(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]),
                        EquipSlot.valueOf(p[4]), Integer.parseInt(p[5])));
                case "A" -> inv.slots.add(new Armor(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]),
                        EquipSlot.valueOf(p[4]), Integer.parseInt(p[5])));
                case "C" -> inv.slots.add(new Consumable(Integer.parseInt(p[1]), p[2], Double.parseDouble(p[3]),
                        Integer.parseInt(p[4]), Integer.parseInt(p[5])));
                case "E" -> inv.equippedItems.put(EquipSlot.valueOf(p[1]), inv.slots.get(Integer.parseInt(p[2])));
            }
        }
        return inv;
    }
}
