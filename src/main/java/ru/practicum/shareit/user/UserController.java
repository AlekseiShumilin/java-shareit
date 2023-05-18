package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        log.info("get-all-users");
        List<User> users = userService.findAll();
        log.info("get-all-users completed: users list size - {}", users.size());
        return users;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer id) {
        log.info("get-user: id - {}", id);
        User user = userService.getUser(id);
        log.info("get-user completed: user - {}", user);
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        log.info("add-user {}", userDto);
        User user = userService.create(userDto);
        log.info("add-user completed: user - {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody UserDto userDto,
                           @PathVariable("userId") Integer userId) {
        log.info("update-user {}", userId);
        User user = userService.update(userDto, userId);
        log.info("update-user completed: user - {}", user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        log.info("delete-user: {} - userId", userId);
        userService.deleteUser(userId);
        log.info("delete-user completed: {} - userId", userId);
    }
}
