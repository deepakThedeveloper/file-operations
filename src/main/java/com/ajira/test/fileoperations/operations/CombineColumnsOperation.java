package com.ajira.test.fileoperations.operations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ajira.test.fileoperations.util.FileServiceUtil.validateMultipleColumnPresence;

public class CombineColumnsOperation implements DataOperations {

    /**
     * Combine multiple columns into single column.
     * Columns that needs to be combined needs to be mentioned in .properties file separated by |
     * @param originalData input file data
     * @param configProp combine columns config file properties
     * @return modified data
     */
    @Override
    public List<Map<String, String>> performOperation(
            final List<Map<String, String>> originalData, final Properties configProp) {

        String columnsToCombine = configProp.getProperty("columnsToCombine");
        String newColumnName = configProp.getProperty("newColumnName");

        String[] multipleColumns = columnsToCombine.split("\\|");
        validateMultipleColumnPresence(originalData.get(0), multipleColumns);

        Set<String> columns = Stream.of(multipleColumns)
                .map(String::trim).collect(Collectors.toSet());
        List<Map<String, String>> modifiedData = new ArrayList<>();
        int i;
        for (Map<String, String> rowData : originalData) {
            String[] joiners = new String[columns.size()];
            i = 0;
            Map<String, String> newMap = new LinkedHashMap<>();
            for (Map.Entry<String, String> map : rowData.entrySet()) {
                if (columns.contains(map.getKey())) {
                    joiners[i] = map.getValue();
                    i++;
                } else {
                    newMap.put(map.getKey(), map.getValue());
                }
            }
            newMap.put(newColumnName, String.join(", ", joiners));
            modifiedData.add(newMap);
        }
        return modifiedData;
    }
}
