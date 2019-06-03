package com.epam.cwlhub.dao;

public class UserException extends RuntimeException{
    public UserException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}

