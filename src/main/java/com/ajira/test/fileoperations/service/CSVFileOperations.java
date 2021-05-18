package com.ajira.test.fileoperations.service;

import com.ajira.test.fileoperations.exceptions.InvalidFilePathException;
import com.ajira.test.fileoperations.exceptions.InvalidInputFileException;
import com.ajira.test.fileoperations.operations.DataOperations;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.collections.CollectionUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ajira.test.fileoperations.data.Constants.FileExtensions.CSV_EXTENSION;
import static com.ajira.test.fileoperations.data.Constants.FileExtensions.PROPERTIES_EXTENSION;
import static com.ajira.test.fileoperations.util.FileServiceUtil.validFileFormatAndPath;

public class CSVFileOperations implements FileOperations {

    @Override
    public Optional<String> performFileOperations(String[] filePaths) throws Exception {
        String inputFile = filePaths[0];
        String configFile = filePaths[1];
        String outputFile = filePaths[2];

        boolean validFile =
                validFileFormatAndPath(inputFile, CSV_EXTENSION) &&
                        validFileFormatAndPath(configFile, PROPERTIES_EXTENSION) &&
                        validFileFormatAndPath(outputFile, CSV_EXTENSION);

        if (!validFile) {
            throw new InvalidFilePathException("Invalid file format or file not found.");
        }
        List<Map<String, String>> originalData = readFile(inputFile);
        if (CollectionUtils.isEmpty(originalData)) {
            return Optional.empty();
        }
        List<Map<String, String>> modifiedData = DataOperations
                .performOperationOnDataBasedOnConfiguration(originalData, configFile);
        if (CollectionUtils.isEmpty(originalData)) {
            return Optional.empty();
        }
        return Optional.of(writeFile(outputFile, modifiedData));
    }

    private List<Map<String, String>> readFile(final String inputFile)
            throws Exception {

        List<Map<String, String>> originalData = new ArrayList<>();
        try (FileReader inputFileReader = new FileReader(inputFile);
             CSVReader csvReader = new CSVReader(inputFileReader)) {

            String[] columnHeaders = csvReader.readNext();
            if (columnHeaders == null || columnHeaders.length == 0) {
                throw new InvalidInputFileException("Input file is empty");
            }

            int columnHeaderLength = columnHeaders.length;
            String[] data;

            for (int i = 1; (data = csvReader.readNext()) != null; i++) {
                if (data.length > columnHeaderLength) {
                    throw new
                            InvalidInputFileException("comma separated values in row:" + i + " is more than columns");
                }
                String[] finalData = data;
                LinkedHashMap<String, String> headerDataMap =
                        IntStream
                                .range(0, columnHeaders.length)
                                .boxed()
                                .collect(Collectors.toMap(o -> columnHeaders[o].trim(), o -> finalData[o].trim(),
                                        (t1, t2) -> t1, LinkedHashMap::new));
                originalData.add(headerDataMap);
            }
        }
        return originalData;
    }

    private String writeFile(final String outputFilePath, final List<Map<String, String>> modifiedData)
            throws IOException {

        List<String[]> modifiedDataList = new ArrayList<>();
        String[] columnHeaders = Optional
                .ofNullable(modifiedData)
                .orElse(Collections.emptyList())
                .get(0)
                .keySet()
                .toArray(new String[0]);

        modifiedDataList.add(columnHeaders);

        for (Map<String, String> map : modifiedData) {
            String[] data = map.values().toArray(new String[0]);
            modifiedDataList.add(data);
        }

        try (FileWriter fileWriter = new FileWriter(outputFilePath);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {
            csvWriter.writeAll(modifiedDataList);
        }
        return outputFilePath;
    }
}
