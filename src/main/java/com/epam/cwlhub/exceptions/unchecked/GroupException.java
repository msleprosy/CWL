package com.epam.cwlhub.exceptions.unchecked;

public class GroupException extends RuntimeException {

    public GroupException(String message) {
        super(message);
    }

    public GroupException (String message, Exception cause){
        super(message, cause);
    }
}
