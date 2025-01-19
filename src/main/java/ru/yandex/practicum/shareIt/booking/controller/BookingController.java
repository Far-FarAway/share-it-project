package ru.yandex.practicum.shareIt.booking.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.booking.dto.ReceivedBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.SentBookingDto;
import ru.yandex.practicum.shareIt.booking.service.BookingService;
import ru.yandex.practicum.shareIt.maker.OnCreate;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public SentBookingDto bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @Validated({OnCreate.class, Default.class}) @RequestBody ReceivedBookingDto receivedBookingDto) {
        return service.bookItem(userId, receivedBookingDto);
    }

    @PatchMapping("/{bookingId}")
    public SentBookingDto changeBookStatus(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                               @PathVariable long bookingId,
                                               @RequestParam boolean approved) {
        return service.changeBookStatus(ownerId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public SentBookingDto getBooking(@PathVariable long bookingId) {
        return service.getBooking(bookingId);
    }

    @GetMapping
    public List<SentBookingDto> getUserBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                      @RequestParam(defaultValue = "all") String state) {
        return service.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<SentBookingDto> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                @RequestParam(defaultValue = "all") String state) {
        return service.getOwnerBookings(ownerId, state);
    }
}
