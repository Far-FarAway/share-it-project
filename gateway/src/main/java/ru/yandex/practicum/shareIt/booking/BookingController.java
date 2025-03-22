package ru.yandex.practicum.shareIt.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.shareIt.booking.dto.BookItemRequestDto;
import ru.yandex.practicum.shareIt.booking.dto.BookingState;
import ru.yandex.practicum.shareIt.marker.OnCreate;

import java.util.List;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @GetMapping
    public ResponseEntity<List<BookItemRequestDto>> getUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(name = "state", defaultValue = "all") String stateParam) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Get booking with state {}, userId={}", stateParam, userId);
        return bookingClient.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookItemRequestDto>> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                                                     @RequestParam(name = "state", defaultValue = "all") String stateParam) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
        log.info("Get owner booking with state {}, ownerId={}", stateParam, ownerId);
        return bookingClient.getOwnerBookings(ownerId, state);
    }

    @PostMapping
    public ResponseEntity<BookItemRequestDto> bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestBody @Validated(OnCreate.class) BookItemRequestDto requestDto) {
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.bookItem(userId, requestDto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookItemRequestDto> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookItemRequestDto> changeBookStatus(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                                   @PathVariable Long bookingId,
                                                   @RequestParam boolean approved) {
        log.info("Change booking status to {}, userId={}, bookingId={}", approved, ownerId, bookingId);
        return bookingClient.changeBookingStatus(ownerId, bookingId, approved);
    }
}
