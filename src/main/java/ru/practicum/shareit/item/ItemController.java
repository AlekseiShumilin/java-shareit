package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ItemController {
    ItemService itemService;

    @PostMapping("/{itemId}/comment")
    public CommentDto comment(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @RequestBody @Valid CommentDto comment,
                           @PathVariable Long itemId) {
        log.info("comment item: user id - {}, item id - {}, comment - {}", userId, itemId, comment);
        CommentDto commentDto = itemService.comment(userId, itemId, comment);
        log.info("comment item completed: comment - {}", commentDto);
        return commentDto;
    }

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
