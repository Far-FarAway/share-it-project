package ru.yandex.practicum.shareIt.booking;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.yandex.practicum.shareIt.booking.dto.BookItemRequestDto;
import ru.yandex.practicum.shareIt.booking.dto.BookingState;
import ru.yandex.practicum.shareIt.client.BaseClient;
import ru.yandex.practicum.shareIt.exception.BadRequestException;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<List<BookItemRequestDto>> getUserBookings(long userId, BookingState state) {
        Map<String, Object> parameters = Map.of("state", state.name());
        return get("?state={state}", userId, parameters);
    }

    public ResponseEntity<List<BookItemRequestDto>> getOwnerBookings(long ownerId, BookingState state) {
        Map<String, Object> parameters = Map.of("state", state.name());
        return get("/owner?state={state}", ownerId, parameters);
    }

    public ResponseEntity<BookItemRequestDto> bookItem(long userId, BookItemRequestDto requestDto) {
        validated(requestDto);
        System.out.println("fsfhasfghajighiasgfgasuiighighasiaitiiuashuiiuasrhinasfaruvuiarhtijsdfpauihfawrhfijasbfgjapriasrhpiaujsrhf   " + requestDto.getEnd());
        return post("", userId, requestDto);
    }

    public ResponseEntity<BookItemRequestDto> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<BookItemRequestDto> changeBookingStatus(long ownerId, Long bookingId, boolean approved) {
        Map<String, Object> params = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", ownerId, params, null);
    }

    private void validated(BookItemRequestDto booking) {
        Instant start = LocalDateTime.parse(booking.getStart())
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant end = LocalDateTime.parse(booking.getEnd())
                .atZone(ZoneId.systemDefault())
                .toInstant();

        if (end.isBefore(start)) {
            throw new BadRequestException("Окончание бронирования не может быть раньше начала бронирования");
        }

        if (end.equals(start)) {
            throw new BadRequestException("Окончание бронирования не может быть в момент начала бронирования");
        }

        if (end.isBefore(Instant.now())) {
            throw new BadRequestException("Окончание бронирования не может быть в прошлом");
        }

        if (start.isBefore(Instant.now())) {
            throw new BadRequestException("Начало бронирования не может быть в прошлом");
        }
    }
}
