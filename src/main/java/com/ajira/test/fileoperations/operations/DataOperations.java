package com.ajira.test.fileoperations.operations;

import com.ajira.test.fileoperations.data.Operations;
import com.ajira.test.fileoperations.factory.DataOperationObjectFactory;
import com.ajira.test.fileoperations.util.FileServiceUtil;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.ajira.test.fileoperations.data.Constants.ConfigFileConstants.OPERATION;

public interface DataOperations {

    DataOperationObjectFactory objectFactory = DataOperationObjectFactory.INSTANCE;

    static List<Map<String, String>> performOperationOnDataBasedOnConfiguration(
            final List<Map<String, String>> originalData, final String configFile) throws Exception {

        Properties configProp = FileServiceUtil.parsePropertiesFile(configFile);
        String operation = configProp.getProperty(OPERATION);

        return objectFactory.getObject(operation, Operations.getOperationClass(operation))
                .performOperation(originalData, configProp);
    }

    List<Map<String, String>> performOperation(List<Map<String, String>> originalData, Properties configProp);
}
