package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getItem(Integer id);

    Item createItem(ItemDto itemDto, Integer userId);

    Item updateItem(int userId, int itemId, ItemDto itemDto);

    List<Item> getAll(Integer userId);

    List<Item> search(String request);


}
