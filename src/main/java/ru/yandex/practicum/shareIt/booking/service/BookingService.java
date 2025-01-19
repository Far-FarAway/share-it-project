package ru.yandex.practicum.shareIt.booking.service;

import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.ReceivedBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.SentBookingDto;

import java.util.List;

public interface BookingService {
    SentBookingDto bookItem(long userId, ReceivedBookingDto receivedBookingDto);

    SentBookingDto changeBookStatus(long ownerId, long bookingId, boolean approved);

    SentBookingDto getBooking(long bookingId);

    List<SentBookingDto> getUserBookings(Long userId, String state);

    List<SentBookingDto> getOwnerBookings(Long ownerId, String state);
}
