package org.example.exception;

import javax.servlet.ServletException;

public class AuthException extends ServletException {
    public AuthException(String message) {
        super(message);
    }
}
