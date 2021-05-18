package com.ajira.test.fileoperations.util;

import com.ajira.test.fileoperations.exceptions.InvalidInputFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import static com.ajira.test.fileoperations.data.Constants.CustomExceptionMessage.COLUMN_NOT_FOUND;

public class FileServiceUtil {
    public static boolean validFileFormatAndPath(final String filePath, final String expectedFormat) {
        String actualFileFormat = expectedFormat.substring(expectedFormat.lastIndexOf("."));
        return expectedFormat.equals(actualFileFormat) && new File(filePath).exists();
    }

    public static Properties parsePropertiesFile(final String configFile)
        throws IOException{
        InputStream inputStream = new FileInputStream(configFile);
        Properties properties = CustomProperties.INSTANCE;
        properties.load(inputStream);
        inputStream.close();

        return properties;
    }

    public static void validateColumnPresence(Map<String, String> headerDataRow, String column){
        if(!headerDataRow.containsKey(column)){
            throw new InvalidInputFileException(COLUMN_NOT_FOUND +"\"" +column +"\"");
        }
    }

    public static void validateMultipleColumnPresence(Map<String, String> headerDataRow, String[] column){
        Stream.of(column).forEach(col ->validateColumnPresence(headerDataRow, col.trim()));
    }
}
