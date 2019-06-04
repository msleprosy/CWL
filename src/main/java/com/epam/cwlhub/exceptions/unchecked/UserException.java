package com.epam.cwlhub.exceptions.unchecked;

public class UserException extends RuntimeException {
    public UserException(String message, Exception cause) {
        super(message, cause);
    }

    public UserException(String cant_find_by_emai) {
    }
}
