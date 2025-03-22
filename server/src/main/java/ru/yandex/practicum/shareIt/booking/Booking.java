package ru.yandex.practicum.shareIt.booking;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.user.User;

import java.time.Instant;

@Entity
@Table(name = "bookings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    User booker;
    @Enumerated(EnumType.STRING)
    BookStatus status;
    @Column(name = "start_date")
    Instant start;
    @Column(name = "end_date")
    Instant end;

}
