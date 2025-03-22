package ru.yandex.practicum.shareIt.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponse {
    Long itemId;
    Long userId;
    String name;
    String description;
}
