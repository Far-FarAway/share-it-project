package ru.yandex.practicum.shareIt.booking.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.booking.BookStatus;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestBookingDto {
    Long itemId;
    BookStatus status;
    String start;
    String end;
}
