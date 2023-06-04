package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final UserRepository userRepository;

    public Item toItem(ItemDto itemDto, Long userId) {
        Item item = new Item(
                itemDto.getName(),
                itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setUser(userRepository.findById(userId).get());
        return item;
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());
        return itemDto;
    }

}
