//package ru.practicum.shareit;
//
//import lombok.RequiredArgsConstructor;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.practicum.shareit.exception.EmailIsNotAvailableException;
//import ru.practicum.shareit.exception.ItemNotFoundException;
//import ru.practicum.shareit.exception.UserNotFoundException;
//import ru.practicum.shareit.item.ItemService;
//import ru.practicum.shareit.item.dta.ItemStorage;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.user.dao.InMemoryUserStorage;
//import ru.practicum.shareit.user.dto.UserDto;
//import ru.practicum.shareit.user.model.User;
//import ru.practicum.shareit.user.service.UserService;
//
//import java.util.List;
//
//@SpringBootTest
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//class ShareItTests {
//    private final UserService userService;
//    private final ItemService itemService;
//    private final InMemoryUserStorage userStorage;
//    private final ItemStorage itemStorage;
//    User user1;
//    User user2;
//    Item item1;
//    Item item2;
//    Item item3;
//    Item item4;
//
//    @BeforeEach
//    void createUsersAndItems() {
//        UserDto testUserDto = new UserDto(0, "user1name", "user1@mail.ru");
//        user1 = userService.create(testUserDto);
//        UserDto testUser2Dto = new UserDto(0, "user2name", "user2@mail.ru");
//        user2 = userService.create(testUser2Dto);
//        ItemDto itemDto1 = new ItemDto("item1", "item1description", true);
//        item1 = itemService.createItem(itemDto1, 1);
//        ItemDto itemDto2 = new ItemDto("item2", "item2description", true);
//        item2 = itemService.createItem(itemDto2, 2);
//        ItemDto itemDto3 = new ItemDto("item3", "itemSearchTestDescription", true);
//        item3 = itemService.createItem(itemDto3, 2);
//        ItemDto itemDto4 = new ItemDto("item4", "itemSearchTestDescription", false);
//        item4 = itemService.createItem(itemDto4, 2);
//    }
//
//    @AfterEach
//    void clear() {
//        userStorage.deleteUser(1);
//        userStorage.deleteUser(2);
//        userStorage.setIdToOne();
//        itemStorage.deleteItem(1);
//        itemStorage.deleteItem(2);
//        itemStorage.deleteItem(3);
//        itemStorage.deleteItem(4);
//
//    }
//
//    @Test
//    void createUser() {
//        Assertions.assertThat(user1).hasFieldOrPropertyWithValue("name", "user1name")
//                .hasFieldOrPropertyWithValue("email", "user1@mail.ru")
//                .hasFieldOrPropertyWithValue("id", 1);
//    }
//
//    @Test
//    void updateUser() {
//        UserDto updateUserDto = new UserDto(1, "userUpdateName", "userUpdate@mail.ru");
//        user1 = userService.update(updateUserDto, 1);
//        Assertions.assertThat(user1).hasFieldOrPropertyWithValue("name", "userUpdateName")
//                .hasFieldOrPropertyWithValue("email", "userUpdate@mail.ru")
//                .hasFieldOrPropertyWithValue("id", 1);
//    }
//
//    @Test
//    void getUser() {
//        user1 = userService.getUser(1);
//        Assertions.assertThat(user1).hasFieldOrPropertyWithValue("name", "user1name")
//                .hasFieldOrPropertyWithValue("email", "user1@mail.ru")
//                .hasFieldOrPropertyWithValue("id", 1);
//    }
//
//    @Test
//    void shouldReturnNullAfterDeleteUser() {
//        userService.deleteUser(1);
//        Assertions.assertThat(userService.findAll().size()).isEqualTo(1);
//    }
//
//    @Test
//    void shouldReturn2WhenFindAll() {
//        List<User> users = userService.findAll();
//        Assertions.assertThat(users.size()).isEqualTo(2);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenEmailNotAvailable() {
//        UserDto userDto = new UserDto(0, "user1name", "user1@mail.ru");
//        Assertions.assertThatExceptionOfType(EmailIsNotAvailableException.class)
//                .isThrownBy(() -> userService.create(userDto));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenUserNotFound() {
//        Assertions.assertThatExceptionOfType(UserNotFoundException.class)
//                .isThrownBy(() -> userService.getUser(10));
//    }
//
//    @Test
//    void createItem() {
//        Item item = new Item("item1", "item1description");
//        item.setId(1);
//        item.setAvailable(true);
//        item.setOwner(1);
//        Assertions.assertThat(item).isEqualTo(item1);
//    }
//
//    @Test
//    void getItem() {
//        Item item = itemService.getItem(1);
//        Assertions.assertThat(item).isEqualTo(item1);
//    }
//
//    @Test
//    void shouldReturn3WhenGetAllListSize() {
//        Assertions.assertThat(itemService.getAll(2).size()).isEqualTo(3);
//    }
//
//    @Test
//    void updateItemWithAllFields() {
//        ItemDto itemDtoUpdate = new ItemDto("itemUpdate", "itemUpdateDescription", false);
//        Item item = itemService.updateItem(1, 1, itemDtoUpdate);
//        Assertions.assertThat(item).hasFieldOrPropertyWithValue("id", 1)
//                .hasFieldOrPropertyWithValue("name", "itemUpdate")
//                .hasFieldOrPropertyWithValue("description", "itemUpdateDescription")
//                .hasFieldOrPropertyWithValue("isAvailable", false)
//                .hasFieldOrPropertyWithValue("owner", 1);
//    }
//
//    @Test
//    void updateItemWithNameFieldOnly() {
//        ItemDto itemDtoUpdate = new ItemDto("itemUpdate", null, null);
//        Item item = itemService.updateItem(1, 1, itemDtoUpdate);
//        Assertions.assertThat(item).hasFieldOrPropertyWithValue("id", 1)
//                .hasFieldOrPropertyWithValue("name", "itemUpdate")
//                .hasFieldOrPropertyWithValue("description", "item1description")
//                .hasFieldOrPropertyWithValue("isAvailable", true)
//                .hasFieldOrPropertyWithValue("owner", 1);
//    }
//
//    @Test
//    void updateItemWithDescriptionFieldOnly() {
//        ItemDto itemDtoUpdate = new ItemDto(null, "itemUpdateDescription", null);
//        Item item = itemService.updateItem(1, 1, itemDtoUpdate);
//        Assertions.assertThat(item).hasFieldOrPropertyWithValue("id", 1)
//                .hasFieldOrPropertyWithValue("name", "item1")
//                .hasFieldOrPropertyWithValue("description", "itemUpdateDescription")
//                .hasFieldOrPropertyWithValue("isAvailable", true)
//                .hasFieldOrPropertyWithValue("owner", 1);
//    }
//
//    @Test
//    void updateItemWithAvailableFieldOnly() {
//        ItemDto itemDtoUpdate = new ItemDto(null, null, false);
//        Item item = itemService.updateItem(1, 1, itemDtoUpdate);
//        Assertions.assertThat(item).hasFieldOrPropertyWithValue("id", 1)
//                .hasFieldOrPropertyWithValue("name", "item1")
//                .hasFieldOrPropertyWithValue("description", "item1description")
//                .hasFieldOrPropertyWithValue("isAvailable", false)
//                .hasFieldOrPropertyWithValue("owner", 1);
//    }
//
//    @Test
//    void shouldReturn1WhenSearchListSize() {
//        Assertions.assertThat(itemService.search("TesT")).size().isEqualTo(1);
//    }
//
//    @Test
//    void shouldReturnEmptyListWhenSearchFindsNothig() {
//        Assertions.assertThat(itemService.search("AAAAaadff")).size().isEqualTo(0);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenItemNotFound() {
//        Assertions.assertThatExceptionOfType(ItemNotFoundException.class)
//                .isThrownBy(() -> itemService.getItem(10));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenItemUpdateWithUserIdIncorrect() {
//        ItemDto itemDtoUpdate = new ItemDto(null, "itemUpdateDescription", null);
//        Assertions.assertThatExceptionOfType(ItemNotFoundException.class)
//                .isThrownBy(() -> itemService.updateItem(2, 1, itemDtoUpdate));
//    }
//
//
//}
