package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDtoWithBooker create(BookingDto bookingDto, Long bookerId) {
        Item item = checkBooking(bookingDto);
        if(item.getUser().getId().equals(bookerId)) {
            throw new ItemNotFoundException("Item can not be booked by its owner");
        }
        Booking booking = bookingMapper.toBooking(bookingDto, bookerId, item);
        booking.setStatus(BookingStatus.WAITING);
        bookingRepository.save(booking);
        return bookingMapper.toBookingDtoWithBooker(booking);
    }

    @Override
    public BookingDtoWithBooker setStatus(Long userId, Long bookingId, String approve) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id " + bookingId + " not found"));
        if (!booking.getItem().getUser().getId().equals(userId)) {
            throw new ItemNotFoundException("Booking can be approved by its owner only");
        }
        if(booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new BookingRequestStatusException("Status can not be changed after being approved");
        }
        if (approve.equals("true")) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        bookingRepository.save(booking);
        return bookingMapper.toBookingDtoWithBooker(booking);
    }

    @Override
    public BookingDtoWithBooker getBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with id " + bookingId + " not found"));
        if (!booking.getBooker().getId().equals(userId) &&
                !booking.getItem().getUser().getId().equals(userId)) {
            throw new BookingNotFoundException("Booking can be accessed bu its owner or booker only");
        }
        return bookingMapper.toBookingDtoWithBooker(booking);
    }

    @Override
    public List<BookingDtoWithBooker> getAll(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        BookingRequestStatus enumStatus = checkStatus(status);
        List<Booking> bookings = new ArrayList<>();
        switch (enumStatus) {
            case ALL:
                bookings = bookingRepository.findByBooker_idOrderByStartDesc(userId);
                break;
            case WAITING:
                bookings = bookingRepository
                        .findByBooker_idAndStatusOrderByStartDesc(userId, BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository
                        .findByBooker_idAndStatusOrderByStartDesc(userId, BookingStatus.REJECTED);
                break;
            case PAST:
                bookings = bookingRepository
                        .findByBooker_IdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository
                        .findByBooker_IdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case CURRENT:
                bookings = bookingRepository
                        .findByBooker_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId,
                                LocalDateTime.now(),
                                LocalDateTime.now());
                break;
        }
        return bookings.stream()
                .map(bookingMapper::toBookingDtoWithBooker)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDtoWithBooker> getAllForOwner(Long userId, String status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        BookingRequestStatus enumStatus = checkStatus(status);
        List<Booking> bookings = new ArrayList<>();
        switch (enumStatus) {
            case ALL:
                bookings = bookingRepository.findByItem_User_IdOrderByStartDesc(userId);
                break;
            case WAITING:
                bookings = bookingRepository
                        .findByItem_User_IdAndStatusOrderByStartDesc(userId, BookingStatus.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository
                        .findByItem_User_IdAndStatusOrderByStartDesc(userId, BookingStatus.REJECTED);
                break;
            case PAST:
                bookings = bookingRepository
                        .findByItem_User_IdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository
                        .findByItem_User_IdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case CURRENT:
                bookings = bookingRepository
                        .findByItem_User_IdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId,
                                LocalDateTime.now(),
                                LocalDateTime.now());
                break;
        }
        return bookings.stream()
                .map(bookingMapper::toBookingDtoWithBooker)
                .collect(Collectors.toList());
    }


    Item checkBooking(BookingDto bookingDto) {
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + bookingDto.getItemId() +
                        " not found"));
        if (!item.isAvailable()) {
            throw new ItemUnavailableException("Item is with id " + item.getId() + " is not available now");
        }
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) ||
                bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new BookingCreateException("Start date can't be after end date");
        }
        return item;
    }

    BookingRequestStatus checkStatus(String request) {
        return Arrays
                .stream(BookingRequestStatus.values())
                .filter(s -> s.name().equals(request.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new BookingRequestStatusException("Unknown state: UNSUPPORTED_STATUS"));
    }
}
