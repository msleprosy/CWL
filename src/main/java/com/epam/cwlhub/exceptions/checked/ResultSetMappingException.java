package com.epam.cwlhub.exceptions.checked;

import com.epam.cwlhub.exceptions.ExceptionMeta;

import static com.epam.cwlhub.exceptions.ExceptionMeta.JDBC_RESULT_SET_MAPPING_EXCEPTION;

public class ResultSetMappingException extends CWLHubCheckedException{
    public ResultSetMappingException(String entityClassName, Exception cause) {
        super(JDBC_RESULT_SET_MAPPING_EXCEPTION.getExtendedDescription(entityClassName),
              JDBC_RESULT_SET_MAPPING_EXCEPTION.getCode(), cause);
    }
}
