package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dta.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final ItemMapper itemMapper;
    private final UserStorage userStorage;

    @Override
    public Item getItem(Integer id) {
        Item item = itemStorage.getItem(id);
        if (item == null) {
            throw new ItemNotFoundException("Item with id " + id + " not found.");
        }
        return item;
    }

    @Override
    public Item createItem(ItemDto itemDto, Integer userId) {
        checkUser(userId);
        Item item = itemMapper.toItem(itemDto, userId);
        itemStorage.create(item);
        return item;
    }

    @Override
    public Item updateItem(int userId, int itemId, ItemDto itemDto) {
        checkUser(userId);
        checkItem(itemId, userId);
        Item item = itemStorage.getItem(itemId);

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        return item;
    }

    @Override
    public List<Item> getAll(Integer userId) {
        return itemStorage.getAll().stream()
                .filter(item -> item.getOwner() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String request) {
        if (request.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAll().stream()
                .filter(Item::isAvailable)
                .filter(item -> item.getName().toLowerCase().contains(request.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(request.toLowerCase()))
                .collect(Collectors.toList());
    }

    void checkUser(Integer userId) {
        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }

    void checkItem(int itemId, int userId) {
        Item item = itemStorage.getItem(itemId);
        if (itemStorage.getItem(itemId) == null) {
            throw new ItemNotFoundException("Item with id " + itemId + " not found.");
        }
        if (item.getOwner() != userId) {
            throw new ItemNotFoundException("Item with id " + itemId + " doesn't belong to user with id " + userId);
        }
    }
}
