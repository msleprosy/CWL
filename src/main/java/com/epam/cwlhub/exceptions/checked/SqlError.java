package com.epam.cwlhub.exceptions.checked;

import com.epam.cwlhub.exceptions.ExceptionMeta;

public class SqlError extends CWLHubCheckedException {
    public SqlError(ExceptionMeta exceptionMeta, Throwable cause) {
        super(exceptionMeta, cause);
    }
}
