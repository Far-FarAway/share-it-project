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

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.booker u " +
            "WHERE u.id = ?1")
    List<Booking> getAllUserBookings(long userId);

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
            "WHERE b.start > CURRENT_TIMESTAMP AND i.id = ?1 " +
            "ORDER BY b.start ASC")
    Booking getNearliestFutureBooking(long itemId);

    @Query("SELECT b " +
            "FROM Booking b " +
            "JOIN FETCH b.item i " +
            "WHERE b.end > CURRENT_TIMESTAMP AND i.id = ?1 " +
            "ORDER BY b.end DESC")
    Booking getNearliestPastBooking(long itemId);

    boolean existsByItemId(long itemId);
}
