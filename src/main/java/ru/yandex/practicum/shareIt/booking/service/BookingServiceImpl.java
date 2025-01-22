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
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.item.service.ItemService;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;
    ItemService itemService;
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
        Booking booking = prepareAndMakeBookingPOJO(userId, requestBookingDto);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id '" + userId + "' не найден");
        }

        if (!booking.getItem().getAvailable()) {
            throw new BadRequestException("Предмет не доступен для бронирования");
        }

        validated(booking);

        booking.setStatus(BookStatus.WAITING);

        return prepareAndMakeBookingDto(bookingRepository.save(booking));
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

        return prepareAndMakeBookingDto(bookingRepository.save(booking));
    }

    @Override
    public ResponseBookingDto getBooking(long bookingId) {
        return prepareAndMakeBookingDto(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь с id '" + bookingId + "' не найдена")));
    }

    @Override
    public List<ResponseBookingDto> getUserBookings(Long userId, String state) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id '" + userId + "' не найден");
        }

        return getBookingsByState(userId, state, "user").stream()
                .sorted(comparator)
                .map(this::prepareAndMakeBookingDto)
                .toList();
    }

    @Override
    public List<ResponseBookingDto> getOwnerBookings(Long ownerId, String state) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("Пользователь с id '" + ownerId + "' не найден");
        }

        return getBookingsByState(ownerId, state, "owner").stream()
                .sorted(comparator)
                .map(this::prepareAndMakeBookingDto)
                .toList();
    }

    private List<Booking> getBookingsByState(Long id, String state, String ownerOrUser) {
        Instant now = Instant.now();
        Map<String, Function<Long, List<Booking>>> userBookingsMap = Map.of(
                "current", userId -> bookingRepository.findByBookerIdAndStartBeforeAndEndAfter(id, now, now),
                "past", userId -> bookingRepository.findByBookerIdAndEndBefore(id, now),
                "future", userId -> bookingRepository.findByBookerIdAndStartAfter(id, now),
                "waiting", userId -> bookingRepository
                        .findByBookerIdAndStatusContaining(id, "WAITING"),
                "rejected", userId -> bookingRepository
                        .findByBookerIdAndStatusContaining(id, "REJECTED")
        );

        Map<String, Function<Long, List<Booking>>> ownerBookingsMap = Map.of(
                "current", ownerId -> bookingRepository
                        .findByItemUserIdAndStartBeforeAndEndAfter(id, now, now),
                "past", ownerId -> bookingRepository.findByItemUserIdAndEndBefore(id, now),
                "future", ownerId -> bookingRepository.findByItemUserIdAndStartAfter(id, now),
                "waiting", ownerId -> bookingRepository
                        .findByItemUserIdAndStatusContaining(id, "WAITING"),
                "rejected", ownerId -> bookingRepository
                        .findByItemUserIdAndStatusContaining(id, "REJECTED")
        );

        Map<String, Function<Long, List<Booking>>> selectedMap =
                ownerOrUser.equals("user") ? userBookingsMap : ownerBookingsMap;

        return selectedMap.getOrDefault(state,
                ownerOrUser.equals("user") ? bookingRepository::findByBookerId : bookingRepository::findByItemUserId
        ).apply(id);
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

    @Override
    public Booking prepareAndMakeBookingPOJO(long userId, RequestBookingDto requestBookingDto) {
        User booker = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id '" + userId + "' не найден"));

        Item item = itemRepository.findById(requestBookingDto.getItemId()).orElseThrow(() ->
                new NotFoundException("Предмет с id '" + requestBookingDto.getItemId() + "' не найден"));

        return mapper.makePOJO(booker, item, requestBookingDto);
    }

    @Override
    public ResponseBookingDto prepareAndMakeBookingDto(Booking booking) {
        ItemDto itemDto = itemService.prepareAndMakeItemDto(booking.getItem(), false);

        return mapper.makeDto(itemDto, booking);
    }
}
