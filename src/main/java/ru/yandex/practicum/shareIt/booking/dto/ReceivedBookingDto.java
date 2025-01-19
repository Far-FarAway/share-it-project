package ru.yandex.practicum.shareIt.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.maker.OnCreate;

@Data
@Builder
public class ReceivedBookingDto {
    @NotNull
    private Long itemId;
    private BookStatus status;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String start;
    @NotNull(groups = {OnCreate.class})
    @NotBlank(groups = {OnCreate.class})
    private String end;
}
