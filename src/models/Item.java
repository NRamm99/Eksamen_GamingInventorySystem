package models;

import enums.ItemType;

public abstract class Item {
    protected int id;
    protected String name;
    protected double weight;
    protected ItemType type;

    protected Item(int id, String name, double weight, ItemType type) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public ItemType getType() {
        return type;
    }

    public String shortInfo() {
        return "Item ID: " + id + "  |  Name: " + name + "  |  Weight: " + weight + " kg" + "  |  Type: " + type;
    }

}
