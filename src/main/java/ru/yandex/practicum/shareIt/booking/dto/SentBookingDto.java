package ru.yandex.practicum.shareIt.booking.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.user.dto.UserDto;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SentBookingDto {
    Long id;
    ItemDto item;
    UserDto booker;
    BookStatus status;
    String start;
    String end;
}
