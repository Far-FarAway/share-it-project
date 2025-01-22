package ru.yandex.practicum.shareIt.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.item.Comment;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public Comment makePOJO(Item item, CommentDto commentDto) {
        return Comment.builder()
                .item(item)
                .authorName(commentDto.getAuthorName())
                .text(commentDto.getText())
                .created(Instant.now())
                .build();
    }

    public CommentDto makeDto(Comment comment) {

        return CommentDto.builder()
                .id(comment.getItem().getId())
                .authorName(comment.getAuthorName())
                .text(comment.getText())
                .created(comment.getCreated().toString())
                .build();
    }
}
