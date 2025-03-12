package ru.yandex.practicum.shareIt.booking.service;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.shareIt.booking.BookStatus;
import ru.yandex.practicum.shareIt.booking.Booking;
import ru.yandex.practicum.shareIt.booking.dto.RequestBookingDto;
import ru.yandex.practicum.shareIt.booking.dto.ResponseBookingDto;
import ru.yandex.practicum.shareIt.booking.mapper.BookingMapper;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.item.service.ItemService;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingServiceUnitTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    ItemService itemService;
    BookingService bookingService;

    User user;
    User booker;
    Item item;
    Booking booking;
    RequestBookingDto reqBooking;

    @BeforeEach
    void before() {
        user = User.builder()
                .id((long) 3)
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

        reqBooking = RequestBookingDto.builder()
                .itemId((long) 4)
                .start(LocalDateTime.now().toString())
                .end(LocalDateTime.now().toString())
                .build();

        bookingService = new BookingServiceImpl(bookingRepository, userRepository, itemRepository,
                itemService, new BookingMapper());
    }

    @Test
    void testChangeStatus() {
        Mockito.when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(booking));
        Mockito.when(itemService.prepareAndMakeItemDto(Mockito.any(Item.class), Mockito.anyBoolean()))
                        .thenReturn(ItemDto.builder().build());
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

       ResponseBookingDto resp = bookingService.changeBookStatus(3,4,true);

        assertThat(resp.getStatus(), is(BookStatus.APPROVED));
    }
}