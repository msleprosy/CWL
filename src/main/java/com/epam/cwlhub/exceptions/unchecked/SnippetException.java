package com.epam.cwlhub.exceptions.unchecked;

public class SnippetException extends RuntimeException {

    public SnippetException(String message, Exception cause) {
        super(message, cause);
    }

    public SnippetException (String message) {
        super(message);
    }
}