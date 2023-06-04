package ru.practicum.shareit.user.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> users = new HashMap<>();
    private static int userId = 1;

    public static void setIdToOne() {
        userId = 1;
    }

    @Override
    public int generateId() {
        return userId++;
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return users.values().stream()
                .map(User::getEmail)
                .noneMatch(s -> s.equals(email));
    }

    @Override
    public void createUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int userId) {
        return users.get(userId);
    }

    @Override
    public void deleteUser(int userId) {
        users.remove(userId);
    }

}
