package com.ajira.test.fileoperations.factory;

import com.ajira.test.fileoperations.operations.DataOperations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Get objects of data operations class dynamically.
 */
public class DataOperationObjectFactory {

    public static final DataOperationObjectFactory INSTANCE = new DataOperationObjectFactory();
    private DataOperationObjectFactory(){}

    private static final Map<String, DataOperations> dataOperationsObjectMap =
            new ConcurrentHashMap<>();

    /**
     * Get object of data operation class from map based on operation if present.
     * Else will create object, put in map and return. So creating object when needed based on
     * class name and using same object multiple times as objects are stateless.
     *
     * @param operationName String operation
     * @param className Operation class name
     * @return DataOperation implementation class object.
     * @throws Exception
     */
    public DataOperations getObject(final String operationName, final Class className)
            throws Exception {
        if (dataOperationsObjectMap.containsKey(operationName)) {
            return dataOperationsObjectMap.get(operationName);
        }
        return createObject(operationName, className);
    }

    private synchronized DataOperations createObject(String operationName, Class className)
            throws Exception {
        if (dataOperationsObjectMap.containsKey(operationName)) {
            return dataOperationsObjectMap.get(operationName);
        }
        Object object = className.newInstance();
        if (object instanceof DataOperations) {
            dataOperationsObjectMap.put(operationName, (DataOperations) object);
            return (DataOperations) object;
        }
        return null;
    }
}
