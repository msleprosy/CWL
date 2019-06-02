package com.epam.cwlhub.dao.group;

public class GroupException extends RuntimeException {

    public GroupException(String message) {
        super(message);
    }

    public GroupException (String message, Exception cause){
        super(message, cause);
    }
}
