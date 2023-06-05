package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto getItem(Long itemId, Long userId);

    ItemDto createItem(ItemDto itemDto, Long userId);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    List<ItemDto> getAll(Long userId);

    List<ItemDto> search(String request);
    CommentDto comment(Long userId, Long itemId, CommentDto comment);


}
