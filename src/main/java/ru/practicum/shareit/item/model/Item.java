package ru.practicum.shareit.item.model;

import lombok.Data;

@Data
public class Item {
    private int id;
    private String name;
    private String description;
    private boolean isAvailable;
    private int owner;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
