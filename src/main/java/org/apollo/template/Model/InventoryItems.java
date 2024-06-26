package org.apollo.template.Model;

public class InventoryItems {

    private int id;
    private String name;
    private String description;

    public InventoryItems(String name) {
        this.name = name;
    }

    public InventoryItems(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public InventoryItems(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public InventoryItems(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public InventoryItems(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
