package ru.practicum.shareit.booking.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingMapper {
    UserRepository userRepository;
    ItemMapper itemMapper;

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
        bookingDto.setItem(itemMapper.toItemDto(booking.getItem()));
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBooker(booking.getBooker());
        return bookingDto;
    }
    public BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setItem(itemMapper.toItemDto(booking.getItem()));
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setBookerId(booking.getBooker().getId());
        return bookingDto;
    }
}
