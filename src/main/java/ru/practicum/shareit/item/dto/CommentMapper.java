package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentMapper {
    UserRepository userRepository;
    ItemRepository itemRepository;

    public Comment toComment(CommentDto commentDto, Long userId, Long itemId) {
        Comment comment = new Comment();
        comment.setAuthor(userRepository.findById(userId).get());
        comment.setText(commentDto.getText());
        comment.setItem(itemRepository.findById(itemId).get());
        return comment;
    }

    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setCreated(LocalDateTime.now());
        return commentDto;
    }
}
