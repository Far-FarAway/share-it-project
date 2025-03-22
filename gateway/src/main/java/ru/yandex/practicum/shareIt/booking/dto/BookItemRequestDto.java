package ru.yandex.practicum.shareIt.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookItemRequestDto {
    @NotNull
    long itemId;
    BookingState status;
    @NotNull
    @NotBlank
    String start;
    @NotNull
    @NotBlank
    String end;
}
