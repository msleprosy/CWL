package com.epam.cwlhub.exceptions;

public enum ExceptionMeta {
    SQL_EXECUTION_ERROR(1, "Can't execute sql statement properly."),
    JDBC_RESULT_SET_MAPPING_EXCEPTION(10, "Can't map result set to an entity of class %s.");

    private int code;
    private String description;

    ExceptionMeta(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getExtendedDescription(String supplement) {
        return String.format(description, supplement);
    }
}
