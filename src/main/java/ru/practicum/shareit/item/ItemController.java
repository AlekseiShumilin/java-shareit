package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @RequestBody @Valid ItemDto itemDto) {
        log.info("create-item: user id - {}, item - {}", userId, itemDto);
        Item item = itemService.createItem(itemDto, userId);
        log.info("create-item completed: user id - {}, item - {}", userId, item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @PathVariable Integer itemId,
                           @RequestBody ItemDto itemDto) {
        log.info("edit-item: item id - {}", itemId);
        Item updatedItem = itemService.updateItem(userId, itemId, itemDto);
        log.info("edit-item completed: edited item - {}", updatedItem);
        return updatedItem;
    }

    @GetMapping("/{itemId}")
    public Item getItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                        @PathVariable Integer itemId) {
        log.info("get-item: item id - {}", itemId);
        Item item = itemService.getItem(itemId);
        log.info("get-item completed: item id - {}", itemId);
        return item;
    }

    @GetMapping
    public List<Item> getAll(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("get-all");
        List<Item> items = itemService.getAll(userId);
        log.info("get-all completed: list length - {}", items.size());
        return items;
    }

    @GetMapping("/search")
    public List<Item> search(@RequestHeader("X-Sharer-User-Id") Integer userId,
                             @RequestParam String text) {
        log.info("get-all");
        List<Item> items = itemService.search(text);
        log.info("get-all completed: list length - {}", items.size());
        return items;
    }
}
