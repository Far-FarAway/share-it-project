package ru.yandex.practicum.shareIt.item.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;
    Integer bookCount;
    String nextBooking;
    String lastBooking;
    List<CommentDto> comments;
    Long requestId;
}
