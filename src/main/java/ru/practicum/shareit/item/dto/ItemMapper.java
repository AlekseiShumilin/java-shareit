package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemMapper {
    UserRepository userRepository;
    CommentRepository commentRepository;
    CommentMapper commentMapper;

    public Item toItem(ItemDto itemDto, Long userId) {
        Item item = new Item(
                itemDto.getName(),
                itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setUser(userRepository.findById(userId).get());
        return item;
    }

    public ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());

        List<CommentDto> comments = commentRepository.findAllByItemId(item.getId())
                .stream().map(commentMapper::toCommentDto).collect(Collectors.toList());

        itemDto.setComments(comments);
        return itemDto;
    }

}
