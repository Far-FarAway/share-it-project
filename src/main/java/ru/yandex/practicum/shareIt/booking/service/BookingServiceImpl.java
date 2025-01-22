package ru.yandex.practicum.shareIt.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
import ru.yandex.practicum.shareIt.booking.mapper.BookingMapper;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.exception.BadRequestException;
import ru.yandex.practicum.shareIt.exception.ConditionsNotMatchException;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    UserRepository userRepository;
    BookingMapper mapper;
    Comparator<Booking> comparator = new Comparator<Booking>() {
        @Override
        public int compare(Booking o1, Booking o2) {
            if (o1.getStart().isAfter(o2.getStart())) {
                return 3;
            } else if (o1.getStart().isBefore(o2.getStart())) {
                return -3;
            } else {
                return 0;
            }
        }
    };

    @Override
    public ResponseBookingDto bookItem(long userId, RequestBookingDto requestBookingDto) {
        Booking booking = mapper.makePOJO(userId, requestBookingDto);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id '" + userId + "' не найден");
        }

        if (!booking.getItem().getAvailable()) {
            throw new BadRequestException("Предмет не доступен для бронирования");
        }

        validated(booking);

        booking.setStatus(BookStatus.WAITING);

        return mapper.makeDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto changeBookStatus(long ownerId, long bookingId, boolean approved) {
        BookStatus status = approved ? BookStatus.APPROVED : BookStatus.REJECTED;
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с id '" + bookingId + "' не найдена"));

        if (booking.getItem().getUser().getId() != ownerId) {
            throw new ConditionsNotMatchException("Только владелец может изменять статус брони");
        }

        booking.setStatus(status);

        return mapper.makeDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto getBooking(long bookingId) {
        return mapper.makeDto(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с id '" + bookingId + "' не найдена")));
    }

    @Override
    public List<ResponseBookingDto> getUserBookings(Long userId, String state) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id '" + userId + "' не найден");
        }

        return getBookingsByState(userId, state, "user").stream()
                .sorted(comparator)
                .map(mapper::makeDto)
                .toList();
    }

    @Override
    public List<ResponseBookingDto> getOwnerBookings(Long ownerId, String state) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("Пользователь с id '" + ownerId + "' не найден");
        }

        return getBookingsByState(ownerId, state, "owner").stream()
                .sorted(comparator)
                .map(mapper::makeDto)
                .toList();
    }

    private List<Booking> getBookingsByState(Long id, String state, String ownerOrUser) {
        Instant now = Instant.now();

        switch (state) {
            case "current" -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerIdAndStartBeforeAndEndAfter(id, now, now);
                } else {
                    return bookingRepository.findByItemUserIdAndStartBeforeAndEndAfter(id, now, now);
                }
            }
            case "past" -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerIdAndEndBefore(id, now);
                } else {
                    return bookingRepository.findByItemUserIdAndEndBefore(id, now);
                }
            }
            case "future" -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerIdAndStartAfter(id, now);
                } else {
                    return bookingRepository.findByItemUserIdAndStartAfter(id, now);
                }
            }
            case "waiting" -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerIdAndStatusContaining(id,  "WAITING");
                } else {
                    return bookingRepository.findByItemUserIdAndStatusContaining(id, "WAITING");
                }
            }
            case "rejected" -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerIdAndStatusContaining(id, "REJECTED");
                } else {
                    return bookingRepository.findByItemUserIdAndStatusContaining(id, "REJECTED");
                }
            }
            default -> {
                if (ownerOrUser.equals("user")) {
                    return bookingRepository.findByBookerId(id);
                } else {
                    return bookingRepository.findByItemUserId(id);
                }
            }
        }
    }

    private void validated(Booking booking) {
        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new BadRequestException("Окончание бронирования не может быть раньше начала бронирования");
        }

        if (booking.getEnd().equals(booking.getStart())) {
            throw new BadRequestException("Окончание бронирования не может быть в момент начала бронирования");
        }

        if (booking.getEnd().isBefore(Instant.now())) {
            throw new BadRequestException("Окончание бронирования не может быть в прошлом");
        }

        if (booking.getStart().isBefore(Instant.now())) {
            throw new BadRequestException("Начало бронирования не может быть в прошлом");
        }
    }
}
