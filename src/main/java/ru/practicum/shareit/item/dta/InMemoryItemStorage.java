package ru.practicum.shareit.item.dta;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();

    public Long generateId() {
        long id;
        if (items.isEmpty()) {
            id = 1L;
        } else {
            id = items.keySet()
                    .stream()
                    .mapToLong(Long::valueOf)
                    .max()
                    .getAsLong();
            id++;
        }
        return id;
    }

    @Override
    public void create(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public List<Item> getAll() {
        return items.values().parallelStream().collect(Collectors.toList());
    }

    @Override
    public Item getItem(Long id) {
        return items.get(id);
    }

    @Override
    public void updateItem(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void deleteItem(Long id) {
        items.remove(id);
    }
}
