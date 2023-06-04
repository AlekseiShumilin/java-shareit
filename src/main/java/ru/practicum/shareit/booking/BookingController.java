package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoWithBooker create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestBody @Valid BookingDto bookingDto) {
        log.info("create booking: user id - {}, booking - {}", userId, bookingDto);
        BookingDtoWithBooker booking = bookingService.create(bookingDto, userId);
        log.info("create booking completed: booking - {}", booking);
        return booking;
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoWithBooker bookingResponse(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable("bookingId") Long bookingId,
                                      @RequestParam("approved") String approved) {
        log.info("set booking status: booking id - {}, status - {}", bookingId, approved);
        BookingDtoWithBooker booking = bookingService.setStatus(userId, bookingId, approved);
        log.info("set booking status completed: booking - {}", booking);
        return booking;
    }

    @GetMapping("/{bookingId}")
    public BookingDtoWithBooker getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable Long bookingId) {
        log.info("get booking: booking id - {}", bookingId);
        BookingDtoWithBooker booking = bookingService.getBooking(bookingId, userId);
        log.info("get booking completed: booking - {}", booking);
        return booking;
    }

    @GetMapping
    public List<BookingDtoWithBooker> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestParam(required = false, defaultValue = "ALL") String state) {
        log.info("get all bookings: user id - {}, state - {}", userId, state);
        List<BookingDtoWithBooker> bookings = bookingService.getAll(userId, state);
        log.info("get all bookings: bookings - {}", bookings);
        return bookings;
    }
    @GetMapping("/owner")
    public List<BookingDtoWithBooker> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestParam(required = false, defaultValue = "ALL") String state) {
        log.info("get all bookings: user id - {}, state - {}", userId, state);
        List<BookingDtoWithBooker> bookings = bookingService.getAllForOwner(userId, state);
        log.info("get all bookings: bookings - {}", bookings);
        return bookings;
    }
}
