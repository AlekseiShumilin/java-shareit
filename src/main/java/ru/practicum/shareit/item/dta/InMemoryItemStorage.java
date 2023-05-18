package ru.practicum.shareit.item.dta;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InMemoryItemStorage implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();

    public int generateId() {
        int id;
        if (items.isEmpty()) {
            id = 1;
        } else {
            id = items.keySet()
                    .stream()
                    .mapToInt(Integer::valueOf)
                    .max()
                    .getAsInt();
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
    public Item getItem(Integer id) {
        return items.get(id);
    }

    @Override
    public void updateItem(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void deleteItem(Integer id) {
        items.remove(id);
    }
}
