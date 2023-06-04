package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody @Valid ItemDto itemDto) {
        log.info("create-item: user id - {}, item - {}", userId, itemDto);
        ItemDto item = itemService.createItem(itemDto, userId);
        log.info("create-item completed: user id - {}, item - {}", userId, item);
        return item;
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable Long itemId,
                           @RequestBody ItemDto itemDto) {
        log.info("edit-item: item id - {}", itemId);
        ItemDto updatedItem = itemService.updateItem(userId, itemId, itemDto);
        log.info("edit-item completed: edited item - {}", updatedItem);
        return updatedItem;
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                        @PathVariable Long itemId) {
        log.info("get-item: item id - {}", itemId);
        ItemDto item = itemService.getItem(itemId, userId);
        log.info("get-item completed: item - {}", item);
        return item;
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("get-all");
        List<ItemDto> items = itemService.getAll(userId);
        log.info("get-all completed: list length - {}", items.size());
        return items;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @RequestParam String text) {
        log.info("get-all");
        List<ItemDto> items = itemService.search(text);
        log.info("get-all completed: list length - {}", items.size());
        return items;
    }
}
