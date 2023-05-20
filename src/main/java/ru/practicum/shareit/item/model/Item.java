package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    int id;
    String name;
    String description;
    boolean isAvailable;
    int owner;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
