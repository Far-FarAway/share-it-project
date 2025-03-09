package ru.yandex.practicum.shareIt.item.dto;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    @Positive
    long id;
    String authorName;
    String text;
    String created;
}
