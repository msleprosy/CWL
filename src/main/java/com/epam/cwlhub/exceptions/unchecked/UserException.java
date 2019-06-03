package com.epam.cwlhub.exceptions.unchecked;

public class UserException extends RuntimeException {
    public UserException(String message, Exception cause) {
        super(message, cause);
    }
}
