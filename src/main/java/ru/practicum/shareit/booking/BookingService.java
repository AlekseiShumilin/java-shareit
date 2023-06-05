package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;

import java.util.List;

public interface BookingService {
    BookingDtoWithBooker create(BookingDto bookingDto, Long bookerId);
    BookingDtoWithBooker setStatus(Long userId, Long bookingId, String approve);
    BookingDtoWithBooker getBooking(Long bookingId, Long userId);
    List<BookingDtoWithBooker> getAll (Long userId, String status);
    List<BookingDtoWithBooker> getAllForOwner(Long userId, String status);

}
