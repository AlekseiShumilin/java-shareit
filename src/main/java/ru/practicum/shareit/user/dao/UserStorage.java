package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAll();

    User getUser(int userId);

    void deleteUser(int userId);

    int generateId();

    void createUser(User user);

    boolean isEmailAvailable(String email);

}
