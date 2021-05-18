package com.ajira.test.fileoperations.operations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ajira.test.fileoperations.util.FileServiceUtil.validateColumnPresence;

public class NewColumnOperation extends DataFilteringOperation implements DataOperations {

    /**
     * Create new column based on data of existing column.
     * @param originalData input file data
     * @param configProp new column config file properties
     * @return data with new column.
     */
    @Override
    public List<Map<String, String>> performOperation(
            final List<Map<String, String>> originalData, final Properties configProp) {
        String basedOnColumn = configProp.getProperty("basedOnColumn");
        String columnType = configProp.getProperty("columnType");
        String condition = configProp.getProperty("condition");
        String conditionValue = configProp.getProperty("conditionValue");
        String newColumnValue = configProp.getProperty("newColumnValue");
        String newColumnName = configProp.getProperty("newColumnName");

        validateColumnPresence(originalData.get(0), basedOnColumn.trim());
        String[] newColumnValues = newColumnValue.split("\\|");


        Predicate<String> isConditionValid = getPredicateForCondition(columnType, condition, conditionValue);

        return originalData
                .stream()
                .filter(Objects::nonNull)
                .peek(map -> {
                    String val =
                            isConditionValid.test(map.get(basedOnColumn))
                                    ? newColumnValues[0]
                                    : newColumnValues[1];
                    map.put(newColumnName, val.trim());
                }).collect(Collectors.toList());
    }
}
