package ru.yandex.practicum.shareIt.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareIt.booking.Booking;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByItemUserId(long ownerId);

    List<Booking> findByItemUserIdAndStartBeforeAndEndAfter(long ownerId, Instant now1, Instant now2);

    List<Booking> findByItemUserIdAndEndBefore(long ownerId, Instant now);

    List<Booking> findByItemUserIdAndStartAfter(long ownerId, Instant now);

    List<Booking> findByItemUserIdAndStatusContaining(long ownerId, String status);

    List<Booking> findByBookerId(long bookerId);

    List<Booking> findByBookerIdAndStartBeforeAndEndAfter(long ownerId, Instant now1, Instant now2);

    List<Booking> findByBookerIdAndEndBefore(long ownerId, Instant now);

    List<Booking> findByBookerIdAndStartAfter(long ownerId, Instant now);

    List<Booking> findByBookerIdAndStatusContaining(long ownerId, String status);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "WHERE i.id = ?1 AND b.start >= CURRENT_TIMESTAMP " +
            "ORDER BY b.start ASC " +
            "LIMIT 1")
    Booking getNearliestFutureBooking(long itemId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "WHERE i.id = ?1 AND b.end < CURRENT_TIMESTAMP " +
            "ORDER BY b.end DESC " +
            "LIMIT 1")
    Booking getNearliestPastBooking(long itemId);

    boolean existsByItemId(long itemId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "JOIN b.booker br " +
            "WHERE br.id = ?1 AND " +
            "b.end < CURRENT_TIMESTAMP")
    boolean existsValidBookingForAddComment(long userId);
}
