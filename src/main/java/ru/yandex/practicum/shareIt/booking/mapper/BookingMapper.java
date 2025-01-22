package ru.yandex.practicum.shareIt.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
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

    public Booking makePOJO(Long userId, RequestBookingDto requestBookingDto) {
        return Booking.builder()
                .item(itemRepository.findById(requestBookingDto.getItemId()).orElseThrow(() ->
                        new NotFoundException("Предмет с id '" + requestBookingDto.getItemId() + "' не найден")))
                .booker(userRepository.findById(userId).orElseThrow(() ->
                        new NotFoundException("Пользователь с id '" + userId + "' не найден")))
                .status(requestBookingDto.getStatus())
                .start(LocalDateTime.parse(requestBookingDto.getStart())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .end(LocalDateTime.parse(requestBookingDto.getEnd())
                        .atZone(ZoneId.systemDefault())
                        .toInstant())
                .build();
    }

    public ResponseBookingDto makeDto(Booking booking) {
        return ResponseBookingDto.builder()
                .id(booking.getId())
                .item(itemMapper.makeDto(booking.getItem(), false))
                .booker(UserMapper.makeDto(booking.getBooker()))
                .status(booking.getStatus())
                .start(LocalDateTime.ofInstant(booking.getStart(), ZoneId.systemDefault()).toString())
                .end(LocalDateTime.ofInstant(booking.getEnd(), ZoneId.systemDefault()).toString())
                .build();
    }
}
