package com.ajira.test.fileoperations.operations;

import com.ajira.test.fileoperations.exceptions.InvalidConfigurationFileException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ajira.test.fileoperations.data.Constants.DataFilter.*;
import static com.ajira.test.fileoperations.util.FileServiceUtil.validateColumnPresence;

public class DataFilteringOperation implements DataOperations {

    /**
     * Filter data based on provided condition in config file.
     * @param originalData input file data
     * @param configProp data filtering config file properties
     * @return modified data.
     */
    @Override
    public List<Map<String, String>> performOperation(final List<Map<String, String>> originalData,
                                                      final Properties configProp) {

        String columnType = configProp.getProperty("columnType");
        String columnToFilter = configProp.getProperty("columnToFilter");
        String condition = configProp.getProperty("condition");
        String value = configProp.getProperty("value");

        validateColumnPresence(originalData.get(0), columnToFilter.trim());
        Predicate<String> isConditionValid = getPredicateForCondition(columnType, condition, value);

        return originalData
                .stream()
                .filter(Objects::nonNull)
                .filter(map -> isConditionValid.test(map.get(columnToFilter)))
                .collect(Collectors.toList());
    }

    Predicate<String> getPredicateForCondition(String columnType, String condition, String value) {
        Predicate<String> predicate;
        switch (columnType) {
            case NUMERIC_FILTER:
                predicate = mapValue -> filterNumericData(condition, Integer.parseInt(mapValue), Integer.parseInt(value));
                break;
            case TEXT_FILTER:
                predicate = mapValue -> filterTextData(condition, mapValue, value);
                break;
            default:
                throw new InvalidConfigurationFileException(columnType + " :Not supported");
        }
        return predicate;
    }

    private boolean filterTextData(String condition, String mapValue, String value) {
        boolean result;

        switch (condition) {
            case IS_EMPTY:
                result = mapValue == null || mapValue.isEmpty();
                break;
            case EQUALS:
                result = mapValue!=null && mapValue.equals(value);
                break;
            default:
                throw new InvalidConfigurationFileException(condition+": not supported");
        }
        return result;
    }

    private boolean filterNumericData(String condition, int mapValue, int conditionValue) {
        boolean result;

        switch (condition) {
            case GREATER_THAN:
                result = mapValue > conditionValue;
                break;
            case GREATER_THAN_EQUALS:
                result = mapValue >= conditionValue;
                break;
            case LESSER_THAN:
                result = mapValue < conditionValue;
                break;
            case LESSER_THAN_EQUALS:
                result = mapValue <= conditionValue;
                break;
            case EQUALS:
                result = mapValue == conditionValue;
                break;
            default:
                throw new InvalidConfigurationFileException(condition+": not supported");
        }
        return result;
    }
}
