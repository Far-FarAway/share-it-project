package ru.yandex.practicum.shareIt.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareIt.booking.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1")
    List<Booking> getAllOwnerBookings(long ownerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP BETWEEN b.start AND b.end")
    List<Booking> getCurrentOwnerBookings(long ownerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP > b.end")
    List<Booking> getPastOwnerBookings(long ownerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP < b.start")
    List<Booking> getFutureOwnerBookings(long ownerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1 AND " +
            "b.status = 'WAITING'")
    List<Booking> getWaitingOwnerBookings(long ownerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.user u " +
            "WHERE u.id = ?1 AND " +
            "b.status = 'REJECTED'")
    List<Booking> getRejectedOwnerBookings(long ownerId);

    List<Booking> findByBookerId(long bookerId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP BETWEEN b.start AND b.end")
    List<Booking> getCurrentUserBookings(long userId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP > b.end")
    List<Booking> getPastUserBookings(long userId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1 AND " +
            "CURRENT_TIMESTAMP < b.start")
    List<Booking> getFutureUserBookings(long userId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1 AND " +
            "b.status = 'WAITING'")
    List<Booking> getWaitingUserBookings(long userId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1 AND " +
            "b.status = 'REJECTED'")
    List<Booking> getRejectedUserBookings(long userId);

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
