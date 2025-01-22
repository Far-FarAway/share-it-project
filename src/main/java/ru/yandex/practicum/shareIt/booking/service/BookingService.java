package ru.yandex.practicum.shareIt.booking.service;

import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;

import java.util.List;

public interface BookingService {
    ResponseBookingDto bookItem(long userId, RequestBookingDto requestBookingDto);

    ResponseBookingDto changeBookStatus(long ownerId, long bookingId, boolean approved);

    ResponseBookingDto getBooking(long bookingId);

    List<ResponseBookingDto> getUserBookings(Long userId, String state);

    List<ResponseBookingDto> getOwnerBookings(Long ownerId, String state);

    ResponseBookingDto prepareAndMakeBookingDto(Booking booking);

    Booking prepareAndMakeBookingPOJO(long userId, RequestBookingDto requestBookingDto);
}
