package ru.practicum.shareit.item.dta;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    List<Item> getAll();

    Item getItem(Long id);

    void updateItem(Item item);

    void deleteItem(Long id);

    void create(Item item);

    Long generateId();
}
