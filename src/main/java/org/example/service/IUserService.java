package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;

import java.util.Optional;

public interface IUserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user);
    boolean deleteUser(Long id);
    UserDto getUser(Long id);
    Optional<User> getUserByUsername(String username);
}
