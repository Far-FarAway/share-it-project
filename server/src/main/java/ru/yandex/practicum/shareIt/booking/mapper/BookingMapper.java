package ru.yandex.practicum.shareIt.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class BookingMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");

    public Booking makePOJO(User booker, Item item, RequestBookingDto requestBookingDto) {
        return Booking.builder()
                .item(item)
                .booker(booker)
                .status(requestBookingDto.getStatus())
                .start(LocalDateTime.parse(requestBookingDto.getStart())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .end(LocalDateTime.parse(requestBookingDto.getEnd())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .build();
    }

    public ResponseBookingDto makeDto(ItemDto itemDto, Booking booking) {
        return ResponseBookingDto.builder()
                .id(booking.getId())
                .item(itemDto)
                .booker(UserMapper.makeDto(booking.getBooker()))
                .status(booking.getStatus())
                .start(LocalDateTime.ofInstant(booking.getStart(), ZoneId.systemDefault()).toString())
                .end(LocalDateTime.ofInstant(booking.getEnd(), ZoneId.systemDefault()).toString())
                .build();
    }
}
