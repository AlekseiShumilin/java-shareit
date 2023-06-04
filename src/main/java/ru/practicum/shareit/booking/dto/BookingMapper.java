package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final UserRepository userRepository;

    public Booking toBooking(BookingDto bookingDto, Long bookerId, Item item) {
        Booking booking = new Booking();
        booking.setItem(item);
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setBooker(userRepository.findById(bookerId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + bookerId + " not found")));
        return booking;
    }

    public BookingDtoWithBooker toBookingDtoWithBooker(Booking booking) {
        BookingDtoWithBooker bookingDto = new BookingDtoWithBooker();

        bookingDto.setId(booking.getId());
        bookingDto.setItem(ItemMapper.toItemDto(booking.getItem()));
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBooker(booking.getBooker());
        return bookingDto;
    }
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setItem(ItemMapper.toItemDto(booking.getItem()));
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBookerId(booking.getBooker().getId());
        return bookingDto;
    }
}
