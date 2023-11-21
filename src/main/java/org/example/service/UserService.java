package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.repository.UserRepository;

import java.util.Optional;

public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public UserDto createUser(UserDto user) {
        User userDB = userRepository.create(new User(user.getPassword(), user.getName(), user.getRole()));
        return new UserDto(userDB.getId(), userDB.getPassword(), userDB.getName(), userDB.getRole());
    }

    public UserDto updateUser(UserDto user) {
        User user1 = userRepository.getById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        user1.setName(user.getName());
        user1.setRole(user.getRole());
        User updateUser = userRepository.update(user1);
        return new UserDto(updateUser.getId(), updateUser.getPassword(), updateUser.getName(), updateUser.getRole());
    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    public UserDto getUser(Long id) {
        User userDB = userRepository.getById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(userDB.getId(), userDB.getPassword(), userDB.getName(), userDB.getRole());

    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
