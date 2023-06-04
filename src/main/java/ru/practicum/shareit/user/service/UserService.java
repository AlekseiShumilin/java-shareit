package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User create(UserDto userDto);

    User update(UserDto userDto, Long userId);

    User getUser(Long id);

    void deleteUser(Long userId);

}
