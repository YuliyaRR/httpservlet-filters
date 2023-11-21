package org.example.exception;

import javax.servlet.ServletException;

public class NoAccessException extends ServletException {
    public NoAccessException(String message) {
        super(message);
    }
}
