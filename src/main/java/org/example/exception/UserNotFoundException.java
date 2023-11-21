package org.example.exception;

import javax.servlet.ServletException;

public class UserNotFoundException extends ServletException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
