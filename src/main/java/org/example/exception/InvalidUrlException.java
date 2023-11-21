package org.example.exception;

import javax.servlet.ServletException;

public class InvalidUrlException extends ServletException {
    public InvalidUrlException(String message) {
        super(message);
    }
}
