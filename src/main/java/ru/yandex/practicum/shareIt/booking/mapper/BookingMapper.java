package ru.yandex.practicum.shareIt.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.ReceivedBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.SentBookingDto;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.mapper.ItemMapper;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.user.mapper.UserMapper;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class BookingMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    public Booking makePOJO(Long userId, ReceivedBookingDto receivedBookingDto) {
        return Booking.builder()
                .item(itemRepository.findById(receivedBookingDto.getItemId()).orElseThrow(() ->
                        new NotFoundException("Предмет с id '" + receivedBookingDto.getItemId() + "' не найден")))
                .booker(userRepository.findById(userId).orElseThrow(() ->
                        new NotFoundException("Пользователь с id '" + userId + "' не найден")))
                .status(receivedBookingDto.getStatus())
                .start(LocalDateTime.parse(receivedBookingDto.getStart())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .end(LocalDateTime.parse(receivedBookingDto.getEnd())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .build();
    }

    public SentBookingDto makeDto(Booking booking) {
        return SentBookingDto.builder()
                .id(booking.getId())
                .item(itemMapper.makeDto(booking.getItem(), false))
                .booker(UserMapper.makeDto(booking.getBooker()))
                .status(booking.getStatus())
                .start(LocalDateTime.ofInstant(booking.getStart(), ZoneId.systemDefault()).toString())
                .end(LocalDateTime.ofInstant(booking.getEnd(), ZoneId.systemDefault()).toString())
                .build();
    }
}
