package ru.yandex.practicum.shareIt.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.user.User;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Column(name = "start_date")
    private Instant start;
    @Column(name = "end_date")
    private Instant end;

}
