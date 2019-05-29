package com.epam.cwlhub.exceptions.checked;

import com.epam.cwlhub.exceptions.ExceptionMeta;

public abstract class CWLHubCheckedException extends Exception {
    protected int code;

    public CWLHubCheckedException(String message, int code, Throwable cause) {
        super(message);
        this.code = code;
        initCause(cause);
    }

    public CWLHubCheckedException(ExceptionMeta exceptionMeta, Throwable cause) {
        super(exceptionMeta.getDescription());
        this.code = exceptionMeta.getCode();
        initCause(cause);
    }
}
