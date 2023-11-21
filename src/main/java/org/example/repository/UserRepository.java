package org.example.repository;

import org.example.entity.Role;
import org.example.entity.User;

import java.util.*;

public class UserRepository implements CRUDRepository<User>{

    private static Long id = 0L;

    private static Map<Long, User> users = new HashMap<>();

    static {
        users.put(++id, new User(id, "123", "Ivanov", new HashSet<>(List.of(new Role(1L, "USER")))));
        users.put(++id, new User(id, "321", "PETROV", new HashSet<>(List.of(new Role(1L, "USER"), new Role(2L, "ADMIN")))));
    }

    @Override
    public User create(User user) {
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public User update(User user) {
        users.put(user.getId() ,user);
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return users.remove(id) != null;
    }

    public Optional<User> getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getName().equals(username))
                .findFirst();
    }
}
