package com.epam.cwlhub.exceptions.unchecked;

public class UserException extends RuntimeException{
    public UserException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
