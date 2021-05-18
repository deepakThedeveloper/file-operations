package com.ajira.test.fileoperations.data;

public class Constants {

    public static class ConfigFileConstants {
        public static final String OPERATION = "operation";
    }

    public static class FileExtensions {
        public static final String PROPERTIES_EXTENSION = ".properties";
        public static final String CSV_EXTENSION = ".csv";
    }

    public static class DataFilter {
        public static final String NUMERIC_FILTER = "numeric";
        public static final String TEXT_FILTER = "text";
        public static final String GREATER_THAN = "greater than";
        public static final String GREATER_THAN_EQUALS = "greater than equals";
        public static final String LESSER_THAN = "lesser than";
        public static final String LESSER_THAN_EQUALS = "lesser than equals";
        public static final String EQUALS = "equals";
        public static final String IS_EMPTY = "empty";
    }

    public static class CustomExceptionMessage{
        public static final String COLUMN_NOT_FOUND = "column not present in input file.";
    }
}
