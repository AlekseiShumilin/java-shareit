package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dta.ItemStorage;
import ru.practicum.shareit.item.model.Item;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ItemStorage itemStorage;

    public Item toItem(ItemDto itemDto, Integer userId) {
        Item item = new Item(
                itemDto.getName(),
                itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(userId);
        item.setId(itemStorage.generateId());
        return item;
    }

}
