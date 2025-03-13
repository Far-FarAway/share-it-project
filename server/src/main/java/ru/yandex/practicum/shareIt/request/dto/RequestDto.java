package ru.yandex.practicum.shareIt.request.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.request.ItemResponse;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long id;
    String description;
    List<ItemResponse> items;
    Instant created;
}
