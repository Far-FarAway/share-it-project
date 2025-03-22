package ru.yandex.practicum.shareIt.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.shareIt.booking.dto.BookItemRequestDto;
import ru.yandex.practicum.shareIt.booking.dto.BookingState;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    BookingClient client;
    BookItemRequestDto dto;
    BookItemRequestDto dto2;

    @BeforeEach
    void before() {
        dto = BookItemRequestDto.builder()
                .status(BookingState.REJECTED)
                .itemId(333)
                .build();

        dto2 = BookItemRequestDto.builder()
                .status(BookingState.WAITING)
                .itemId(31213)
                .build();
    }

    @Test
    void testPost() throws Exception {
        Mockito.when(client.bookItem(Mockito.anyLong(), Mockito.any(BookItemRequestDto.class)))
                .thenReturn(ResponseEntity.ok(dto));

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
                        .characterEncoding(StandardCharsets.UTF_8)
                ).andExpect(jsonPath("status", is(dto.getStatus().toString())))
                .andExpect(jsonPath("itemId", is((int) dto.getItemId())));

        Mockito.verify(client, Mockito.times(1))
                .bookItem(1, dto);
    }

    @Test
    void testGetBookingById() throws Exception {
        Mockito.when(client.getBooking(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(ResponseEntity.ok(dto2));

        mvc.perform(get("/bookings/343")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 123)
                ).andExpect(jsonPath("status", is(dto2.getStatus().toString())))
                .andExpect(jsonPath("itemId", is((int) dto2.getItemId())));

        Mockito.verify(client, Mockito.times(1))
                .getBooking(123, (long) 343);
    }

    @Test
    void testGetUserBookings() throws Exception {
        Mockito.when(client.getUserBookings(Mockito.anyLong(), Mockito.any(BookingState.class)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(dto, dto2)));

        mvc.perform(get("/bookings?state=future")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 123)
                ).andExpect(jsonPath("[0].status", is(dto.getStatus().toString())))
                .andExpect(jsonPath("[1]itemId", is((int) dto2.getItemId())));

        Mockito.verify(client, Mockito.times(1))
                .getUserBookings(123, BookingState.FUTURE);
    }

    @Test
    void testGetOwnerBookings() throws Exception {
        Mockito.when(client.getOwnerBookings(Mockito.anyLong(), Mockito.any(BookingState.class)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(dto, dto2)));

        mvc.perform(get("/bookings/owner?state=past")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 776)
                ).andExpect(jsonPath("[1].status", is(dto2.getStatus().toString())))
                .andExpect(jsonPath("[0]itemId", is((int) dto.getItemId())));

        Mockito.verify(client, Mockito.times(1))
                .getOwnerBookings(776, BookingState.PAST);
    }

    @Test
    void patchBooking() throws Exception {
        dto.setStatus(BookingState.APPROVED);
        Mockito.when(client.changeBookingStatus(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean()))
                .thenReturn(ResponseEntity.ok(dto));

        mvc.perform(patch("/bookings/43?approved=true")
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", 98)
        ).andExpect(jsonPath("status", is(dto.getStatus().toString())));

        Mockito.verify(client, Mockito.times(1))
                .changeBookingStatus(98, (long) 43, true);
    }
}