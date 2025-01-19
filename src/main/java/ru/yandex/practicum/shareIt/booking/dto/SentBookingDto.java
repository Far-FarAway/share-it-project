package ru.yandex.practicum.shareIt.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

@Data
@Builder
public class SentBookingDto {
    private Long id;
    private ItemDto item;
    private UserDto booker;
    private BookStatus status;
    private String start;
    private String end;
}
