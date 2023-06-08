package ru.practicum.shareit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoWithBooker;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.BookingRequestStatusException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql("/create_test_data.sql")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShareItDbTest {
    UserService userService;
    ItemService itemService;
    BookingService bookingService;


    @Test
    void shouldReturnValueWhenGetUser() {
        User user = userService.getUser(1L);
        Assertions.assertEquals(1L, (long) user.getId());
        Assertions.assertEquals("user1", user.getName());
        Assertions.assertEquals("user1@mail.ru", user.getEmail());
    }

    @Test
    void shouldReturnListSize5WhenGetAllUsers() {
        List<User> users = userService.findAll();
        Assertions.assertEquals(5, users.size());
    }

    @Test
    void shouldReturnValueWhenCreateNewUser() {
        UserDto newUser = new UserDto(null, "newUser", "newUser@mail.ru");
        userService.create(newUser);
        User user = userService.getUser(6L);
        Assertions.assertEquals(6L, (long) user.getId());
        Assertions.assertEquals("newUser", user.getName());
        Assertions.assertEquals("newUser@mail.ru", user.getEmail());
    }

    @Test
    void shouldReturnValueWhenUpdateUser() {
        UserDto newUser = new UserDto(null, "updatedUser", "updatedUser@mail.ru");
        User user = userService.update(newUser, 1L);
        User updatedUser = userService.getUser(1L);
        Assertions.assertEquals(1L, (long) updatedUser.getId());
        Assertions.assertEquals("updatedUser", updatedUser.getName());
        Assertions.assertEquals("updatedUser@mail.ru", updatedUser.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenGetUserAfterDelete() {
        userService.deleteUser(1L);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void shouldReturnItemDtoAfterCreate() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("item6");
        itemDto.setDescription("item6 description");
        itemDto.setAvailable(true);
        itemService.createItem(itemDto, 3L);

        ItemDto createdItem = itemService.getItem(6L, 3L);
        Assertions.assertEquals(6L, createdItem.getId());
        Assertions.assertEquals("item6", createdItem.getName());
        Assertions.assertEquals("item6 description", createdItem.getDescription());
        Assertions.assertEquals(true, createdItem.getAvailable());
    }

    @Test
    void shouldReturnUpdatedItemAfterUpdate() {
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription("item1 updated description");
        itemDto.setAvailable(false);
        itemService.updateItem(1L, 1L, itemDto);
        ItemDto updatedItem = itemService.getItem(1L, 1L);
        Assertions.assertEquals(1L, updatedItem.getId());
        Assertions.assertEquals("item1", updatedItem.getName());
        Assertions.assertEquals("item1 updated description", updatedItem.getDescription());
        Assertions.assertEquals(false, updatedItem.getAvailable());
    }

    @Test
    void shouldReturnListWith2Items() {
        Assertions.assertEquals(2, itemService.getAll(1L).size());
    }

    @Test
    void shouldReturnItem3() {
        List<ItemDto> items = itemService.search("seAr");
        Assertions.assertEquals(3, items.get(0).getId());
    }

    @Test
    void shouldThrowExceptionWhenGetItemFakeId() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.getItem(100L, 1L));
    }

    @Test
    void shouldThrowExceptionWhenUpdateItemNotOwner() {
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription("item1 updated description");
        itemDto.setAvailable(false);
        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(1L, 5L, itemDto));
    }

    @Test
    void shouldReturnItemWithComment() {
        CommentDto comment = new CommentDto();
        comment.setText("comment");
        comment.setAuthorName("user1");
        comment.setCreated(LocalDateTime.of(2023, 1, 2, 12, 0, 0));
        itemService.comment(2L, 1L, comment);
        ItemDto itemDto = itemService.getItem(1L, 1L);
        Assertions.assertEquals("comment", itemDto.getComments().get(0).getText());
    }

    @Test
    void shouldThrowExceptionWhenCommentWithoutBooking() {
        CommentDto comment = new CommentDto();
        comment.setText("comment");
        comment.setAuthorName("user1");
        comment.setCreated(LocalDateTime.of(2023, 1, 2, 12, 0, 0));
        Assertions.assertThrows(BookingRequestStatusException.class,
                () -> itemService.comment(3L, 1L, comment));
    }

    @Test
    void shouldReturnValueAfterCreateBooking() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(2L);
        bookingDto.setStart(LocalDateTime.of(2023, 8, 1, 1, 0, 0));
        bookingDto.setEnd(LocalDateTime.of(2023, 8, 2, 1, 0, 0));
        bookingService.create(bookingDto, 3L);

        BookingDtoWithBooker savedBooking = bookingService.getBooking(4L, 3L);
        Assertions.assertEquals(4, savedBooking.getId());
        Assertions.assertEquals(BookingStatus.WAITING, savedBooking.getStatus());
    }

    @Test
    void shouldReturnApprovedWhenSetStatus() {
        BookingDtoWithBooker booking = bookingService.setStatus(1L, 2L, "true");
        Assertions.assertEquals(BookingStatus.APPROVED, booking.getStatus());
    }

    @Test
    void shouldReturnListSize2WhenGetAll() {
        List<BookingDtoWithBooker> bookings = bookingService.getAll(2L, "ALL");
        Assertions.assertEquals(3, bookings.size());
    }

    @Test
    void shouldReturnListSize2WhenGetAllByOwner() {
        List<BookingDtoWithBooker> bookings = bookingService.getAllForOwner(1L, "ALL");
        Assertions.assertEquals(2, bookings.size());
    }

    @Test
    void shouldThrowExceptionWhenGetBookingFakeId() {
        Assertions.assertThrows(BookingNotFoundException.class,
                () -> bookingService.getBooking(10L, 1L));
    }
}
