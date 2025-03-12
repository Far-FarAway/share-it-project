package ru.yandex.practicum.shareIt.booking.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.yandex.practicum.shareIt.ShareItServer;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = ShareItServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingServiceIntegrationTest {
    final BookingRepository repository;
    final ItemRepository itemRepository;
    final UserRepository userRepository;
    final BookingService service;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
            .withZone(ZoneId.of("Europe/Moscow"));

    User user;
    User booker;
    Item item;
    Booking booking;

    @BeforeEach
    void before() {
        user = User.builder()
                .name("Shrek")
                .email("shrekIsLove@gmail.com")
                .build();

        booker = User.builder()
                .name("Donkey")
                .email("shrekIsLife@gmail.com")
                .build();

        item = Item.builder()
                .user(user)
                .name("Shrexy pants")
                .description("No words are needed")
                .available(true)
                .build();

        booking = Booking.builder()
                .item(item)
                .booker(booker)
                .status(BookStatus.APPROVED)
                .start(Instant.now().minusSeconds(10))
                .end(Instant.now())
                .build();
    }

    @Test
    void testGetBooking() {
        userRepository.save(user);
        userRepository.save(booker);
        itemRepository.save(item);
        repository.save(booking);

        ResponseBookingDto bookingResp = service.getBooking(booking.getId());

        assertThat(bookingResp.getBooker().getName(), is(booker.getName()));
        assertThat(bookingResp.getItem().getDescription(), is(item.getDescription()));
        assertThat(bookingResp.getStart(), is(formatter.format(booking.getStart())));
    }

    @Test
    void testGetOwnerBooking() {
        userRepository.save(user);
        userRepository.save(booker);
        itemRepository.save(item);
        repository.save(booking);

        List<ResponseBookingDto> resp = service.getOwnerBookings(user.getId(), "past");
        List<ResponseBookingDto> emptyResp = service.getUserBookings(user.getId(), "future");

        assertThat(resp.isEmpty(), is(false));
        assertThat(resp.getFirst().getItem().getName(), is(item.getName()));
        assertThat(emptyResp, empty());
    }

    @Test
    void testGetUserBooking() {
        userRepository.save(user);
        userRepository.save(booker);
        itemRepository.save(item);
        repository.save(booking);

        List<ResponseBookingDto> resp = service.getUserBookings(booker.getId(), "past");
        List<ResponseBookingDto> emptyResp = service.getUserBookings(booker.getId(), "future");

        assertThat(resp.isEmpty(), is(false));
        assertThat(resp.getFirst().getItem().getName(), is(item.getName()));
        assertThat(emptyResp, empty());
    }
}