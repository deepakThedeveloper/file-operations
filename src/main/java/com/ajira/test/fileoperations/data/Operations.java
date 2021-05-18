package com.ajira.test.fileoperations.data;

import com.ajira.test.fileoperations.exceptions.OperationNotSupportedException;
import com.ajira.test.fileoperations.operations.CombineColumnsOperation;
import com.ajira.test.fileoperations.operations.DataFilteringOperation;
import com.ajira.test.fileoperations.operations.DateFormatOperation;
import com.ajira.test.fileoperations.operations.NewColumnOperation;

/**
 * Enum's which represent different operations that needs to be performed on file.
 * Value inside enum is class name of that operation. It helps in generating object of that class dynamically.
 */
public enum Operations {
    FORMAT_DATE(DateFormatOperation.class),
    FILTER_DATA(DataFilteringOperation.class),
    NEW_COLUMN(NewColumnOperation.class),
    COMBINE_COLUMNS(CombineColumnsOperation.class);

    private final Class operationClass;

    Operations(final Class operationClass) {
        this.operationClass = operationClass;
    }

    private Class getClassName() {
        return operationClass;
    }

    /**
     * If provided operation is not listed in enum then enum throws IllegalArgumentException.
     * That exception needs to be caught to send custom exception.
     *
     * @param operation Operation to be be performed on file.
     * @return Operation class.
     */
    public static Class getOperationClass(final String operation) {
        try {
            return valueOf(operation).getClassName();
        } catch (IllegalArgumentException ex) {
            throw new OperationNotSupportedException(operation + " is not supported");
        }
    }
}
