package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_idOrderByStartDesc(Long bookerId);
    List<Booking> findByBooker_idAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);
    List<Booking> findByBooker_IdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);
    List<Booking> findByBooker_IdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime end);
    List<Booking> findByBooker_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Long bookerId, LocalDateTime start, LocalDateTime end);
    List<Booking> findByItem_User_IdOrderByStartDesc(Long bookerId);
    List<Booking> findByItem_User_IdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);
    List<Booking> findByItem_User_IdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);
    List<Booking> findByItem_User_IdAndStartIsAfterOrderByStartDesc(Long userId, LocalDateTime start);
    List<Booking> findByItem_User_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            Long bookerId, LocalDateTime start, LocalDateTime end);
    @Query("select b from Booking b where b.item.id = ?1")
    List<Booking> findBookings(Long itemId);

}
