package ru.yandex.practicum.shareIt.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Comment;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Comment makePOJO(CommentDto commentDto) {
        return Comment.builder()
                .item(itemRepository.findById(commentDto.getId())
                        .orElseThrow(() -> new NotFoundException("Предмет с id '" + commentDto.getId() +
                                "' не найден")))
                .text(commentDto.getText())
                .created(Instant.now())
                .build();
    }

    public CommentDto makeDto(long userId, Comment comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id '" + userId + "' не найден"));
        return CommentDto.builder()
                .id(comment.getItem().getId())
                .authorName(user.getName())
                .text(comment.getText())
                .created(comment.getCreated().toString())
                .build();
    }
}
