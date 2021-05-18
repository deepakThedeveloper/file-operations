package com.ajira.test.fileoperations.operations;

import com.ajira.test.fileoperations.exceptions.FileParsingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ajira.test.fileoperations.util.FileServiceUtil.validateColumnPresence;

public class DateFormatOperation implements DataOperations {

    /**
     * Format date based on format provided mentioned in config file.
     * @param originalData input file data
     * @param configProp date formating config file properties
     * @return data with modified date.
     */
    @Override
    public List<Map<String, String>> performOperation(final List<Map<String, String>> originalData,
                                                      final Properties configProp) {

        String columnToFormat = configProp.getProperty("columnToFormat");
        String fromFormat = configProp.getProperty("from");
        String toFormat = configProp.getProperty("to");

        validateColumnPresence(originalData.get(0), columnToFormat);

        return originalData
                .stream()
                .filter(Objects::nonNull)
                .map(map -> {
                    try {
                        String strDate = map.get(columnToFormat);
                        Date existingDate = new SimpleDateFormat(fromFormat).parse(strDate);

                        String parsedDate = new SimpleDateFormat(toFormat).format(existingDate);
                        map.put(columnToFormat, parsedDate);

                        return map;
                    } catch (ParseException e) {
                        throw new FileParsingException("Incorrect  date format to be parsed");
                    }
                })
                .collect(Collectors.toList());
    }
}
