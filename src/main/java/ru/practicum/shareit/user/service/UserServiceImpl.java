package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailIsNotAvailableException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dao.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userStorage.getAll();
    }

    @Override
    public User create(UserDto userDto) {
        checkEmail(userDto.getEmail());
        User user = userMapper.toUser(userDto);
        userStorage.createUser(user);
        return user;
    }

    @Override
    public User update(UserDto userDto, int userId) {
        User user = getUser(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !user.getEmail().equals(userDto.getEmail())) {
            checkEmail(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        userStorage.createUser(user);
        return user;
    }

    @Override
    public User getUser(Integer id) {
        User user = userStorage.getUser(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return user;
    }

    @Override
    public void deleteUser(Integer userId) {
        getUser(userId);
        userStorage.deleteUser(userId);
    }

    public void checkEmail(String email) {
        if (!userStorage.isEmailAvailable(email)) {
            throw new EmailIsNotAvailableException("Email " + email + " has been already registered.");
        }
    }
}
