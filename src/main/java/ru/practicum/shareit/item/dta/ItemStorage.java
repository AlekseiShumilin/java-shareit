package ru.practicum.shareit.item.dta;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    List<Item> getAll();

    Item getItem(Integer id);

    void updateItem(Item item);

    void deleteItem(Integer id);

    void create(Item item);

    int generateId();
}
