package ru.yandex.practicum.shareIt.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.maker.OnCreate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceivedBookingDto {
    @NotNull
    Long itemId;
    BookStatus status;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String start;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    String end;
}
