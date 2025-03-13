package ru.yandex.practicum.shareIt.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.item.dto.ItemRequestDto;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestReqDto {
    Long id;
    @NotNull
    @NotBlank
    String description;
    List<ItemRequestDto> items;
    Instant created;
}
