package ru.yandex.practicum.shareIt.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
import ru.yandex.practicum.shareIt.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public ResponseBookingDto bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestBody RequestBookingDto requestBookingDto) {
        return service.bookItem(userId, requestBookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto changeBookStatus(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                               @PathVariable long bookingId,
                                               @RequestParam boolean approved) {
        return service.changeBookStatus(ownerId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseBookingDto getBooking(@PathVariable long bookingId) {
        return service.getBooking(bookingId);
    }

    @GetMapping
    public List<ResponseBookingDto> getUserBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestParam(defaultValue = "all") String state) {
        return service.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<ResponseBookingDto> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                     @RequestParam(defaultValue = "all") String state) {
        return service.getOwnerBookings(ownerId, state);
    }
}
