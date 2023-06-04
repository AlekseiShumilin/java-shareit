package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto getItem(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + itemId + " not found."));
        ItemDto itemDto = ItemMapper.toItemDto(item);

        if (userId.equals(item.getUser().getId())) {
            itemDto.setNextBooking(getNextBooking(itemId));
            itemDto.setLastBooking(getLastBooking(itemId));
        }
        return itemDto;
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        checkUser(userId);
        Item item = itemMapper.toItem(itemDto, userId);
        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = checkItem(itemId, userId);

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAll(Long userId) {
        return itemRepository.findAllByUserId(userId).stream()
                .map(ItemMapper::toItemDto)
                .peek(itemDto -> itemDto.setNextBooking(getNextBooking(itemDto.getId())))
                .peek(itemDto -> itemDto.setLastBooking(getLastBooking(itemDto.getId())))
                .sorted(Comparator.comparing(ItemDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String request) {
        if (request.isBlank()) {
            return new ArrayList<>();
        }
        String requestToUpperCase = request.toUpperCase();
        return itemRepository.search(requestToUpperCase).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public BookingDto getLastBooking(Long itemId) {
        return bookingRepository.findBookings(itemId)
                .stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Booking::getEnd).reversed())
                .findFirst()
                .map(BookingMapper::toBookingDto)
                .orElse(null);
    }

    public BookingDto getNextBooking(Long itemId) {
        return bookingRepository.findBookings(itemId)
                .stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.APPROVED))
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Booking::getStart))
                .findFirst()
                .map(BookingMapper::toBookingDto)
                .orElse(null);

    }

    void checkUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    Item checkItem(Long itemId, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + itemId + " not found."));
        if (item.getUser().getId() != userId) {
            throw new ItemNotFoundException("Item with id " + itemId + " doesn't belong to user with id " + userId);
        }
        return item;
    }
}
